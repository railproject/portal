/**
 * Created by star on 5/21/14.
 */

$(function() {
    var app = new ApplicationModel();

    ko.applyBindings(app);
});

// ----------- 应用模型 -------------------
function ApplicationModel() {
    var self = this;

    var tableModel = new TableModel();

    self.tableModel = ko.observable(tableModel);

    self.paramModel = ko.observable(new ParamModel(tableModel));

    self.allBtn = ko.observable(false);

    self.currCheckNbr = ko.observable(0);

    self.checkStatus = ko.computed(function() {
        return "正在校验： " + self.currCheckNbr() + " / " + self.tableModel().planList().length;
    })

    self.canCheckLev1 = ko.computed(function() {
        var flag = false;
        if(self.currCheckNbr() > 0 && self.currCheckNbr() != self.tableModel().planList().length) {
            return flag;
        }
        ko.utils.arrayForEach(self.tableModel().planList(), function(plan) {
            if(plan.needLev1()) {
                flag = true;;
                return true;
            }
        });
        return flag;
    });

    self.canCheckLev2 = ko.computed(function() {
        var flag = false;
        ko.utils.arrayForEach(self.tableModel().planList(), function(plan) {
            if(plan.needLev2()) {
                flag = true;;
                return true;
            }
        });
        return flag;
    });

    self.autoCheck = function() {
        self.currCheckNbr(0);
        ko.utils.arrayForEach(self.tableModel().planList(), function(plan) {
            if(plan.dailyLineFlag() != "已上图" || !plan.dailyLineId()) {
                self.currCheckNbr(self.currCheckNbr() + 1);
                plan.isTrainInfoMatch(-1);
                plan.isTimeTableMatch(-1);
                plan.isRoutingMatch(-1);
            } else {
                $.ajax({
                    url: "audit/plan/" + plan.id() + "/line/" + plan.dailyLineId() + "/check",
                    method: "GET",
                    contentType: "application/json; charset=UTF-8"
                }).done(function(data) {
                    plan.isTrainInfoMatch(data.isTrainInfoMatch);
                    plan.isTimeTableMatch(data.isTimeTableMatch);
                    plan.isRoutingMatch(data.isRoutingMatch);
                }).fail(function() {

                }).always(function() {
                    self.currCheckNbr(self.currCheckNbr() + 1);
                })
            }
        });
    }


    self.selectAllLev1 = function() {
        ko.utils.arrayForEach(self.tableModel().planList(), function(plan) {
            if(self.allBtn()) {
                if(plan.needLev1()) {
                    plan.isSelected(true);
                }
            } else {
                plan.isSelected(false);
            }
        })
        return true;
    }

    self.selectAllLev2 = function() {
        ko.utils.arrayForEach(self.tableModel().planList(), function(plan) {
            if(self.allBtn()) {
                if(plan.needLev2()) {
                    plan.isSelected(true);
                }
            } else {
                plan.isSelected(false);
            }
        })
        return true;
    }

    // 一级审核
    self.checkLev1 = function() {
        $(this).prop( "disabled", true )
        var data = new Array();
        ko.utils.arrayForEach(self.tableModel().planList(), function(plan) {
            if(plan.isSelected()) {
                var paramObj = new Object();
                paramObj.planId = plan.id();
                paramObj.lineId = plan.dailyLineId();
                data.push(paramObj);
            }
        })
        if(data.length <= 0) {
            $(this).prop( "disabled", false );
            return false;
        }
        $.ajax({
            url: "audit/plan/checklev1/1",
            method: "POST",
            dataType: "json",
            data: JSON.stringify(data),
            contentType: "application/json; charset=UTF-8"
        }).done(function(response) {
            ko.utils.arrayForEach(response, function(resp) {
                for(var i = 0; i < self.tableModel().planList().length; i ++) {
                    if(resp.id == self.tableModel().planList()[i].id()) {
                        self.tableModel().planList()[i].checkLev1(resp.checkLev1);
                        self.tableModel().planList()[i].checkLev2(resp.checkLev2);
                        self.tableModel().planList()[i].lev1Checked(resp.lev1Checked);
                        self.tableModel().planList()[i].lev2Checked(resp.lev2Checked);
                        self.tableModel().planList()[i].isSelected(false);
                    }
                }
            });
            $(this).prop( "disabled", false );
            $.gritter.add({
                title: getHintTitle(data.length, response.length),
                text: '审核成功[' + response.length + ']条计划',
                class_name: getHintCss(data.length, response.length),
                image: 'assets/img/screen.png',
                sticky: false,
                time: 3000
            });
        }).fail(function(resp) {
            $(this).prop( "disabled", false );
            $.gritter.add({
                title: '审核出错',
                text: resp,
                class_name: 'growl-danger',
                image: 'assets/img/screen.png',
                sticky: false,
                time: 3000
            });
        }).always(function() {
        })
    }

    // 二级审核
    self.checkLev2 = function() {
        $(this).prop( "disabled", true )
        var data = new Array();
        ko.utils.arrayForEach(self.tableModel().planList(), function(plan) {
            if(plan.isSelected()) {
                var paramObj = new Object();
                paramObj.planId = plan.id();
                paramObj.lineId = plan.dailyLineId();
                data.push(paramObj);
            }
        })
        if(data.length <= 0) {
            $(this).prop( "disabled", false );
            return false;
        }
        $.ajax({
            url: "audit/plan/checklev2/1",
            method: "POST",
            dataType: "json",
            data: JSON.stringify(data),
            contentType: "application/json; charset=UTF-8"
        }).done(function(response) {
            ko.utils.arrayForEach(response, function(resp) {
                for(var i = 0; i < self.tableModel().planList().length; i ++) {
                    if(resp.id == self.tableModel().planList()[i].id()) {
//                        self.tableModel().planList()[i].checkLev1(resp.checkLev1);
//                        self.tableModel().planList()[i].checkLev2(resp.checkLev2);
                        self.tableModel().planList()[i].lev1Checked(resp.lev1Checked);
                        self.tableModel().planList()[i].lev2Checked(resp.lev2Checked);
                        self.tableModel().planList()[i].isSelected(false);
                    }
                }
            });
            $(this).prop( "disabled", false );
            $.gritter.add({
                title:getHintTitle(data.length, response.length),
                text: '审核成功[' + response.length + ']条计划',
                class_name: getHintCss(data.length, response.length),
                image: 'assets/img/screen.png',
                sticky: false,
                time: 3000
            });
        }).fail(function(resp) {
            $(this).prop( "disabled", false );
            $.gritter.add({
                title: '审核出错',
                text: resp,
                class_name: 'growl-danger',
                image: 'assets/img/screen.png',
                sticky: false,
                time: 3000
            });
        }).always(function() {
        })
    }
}

// ########### 页面参数模型 ###############
function ParamModel(tableModel) {
    var self = this;

    self.unknownRunLine = ko.observable(0);

    // 初始化时间控件
    $("#date_selector").datepicker({
        format: "yyyy-mm-dd",
//        weekStart: 1,
        autoclose: true,
        todayBtn: 'linked',
        language: 'zh-CN'}).on('changeDate', function (ev) {
            tableModel.loadTable(moment(ev.date).format("YYYYMMDD"));
            self.loadPies(moment(ev.date).format("YYYYMMDD"));
    });;
    var date = $.url().param("date");
    if (date) {
        $("#date_selector").val(date);
    } else {
        $("#date_selector").datepicker('setValue', new Date());
    };

    self.loadPies = function(date) {
        // 统计图
        $.ajax({
            url: "audit/plan/chart/traintype/" + date,
            method: "GET",
            contentType: "application/json; charset=UTF-8"
        }).done(function(resp) {
            if(resp && resp.length) {
                var name = Array();
                var data = Array();
                var dataArrayFinal = Array();
                for(var i = 0; i < resp.length; i ++) {
                    name[i] = resp[i].name;
                    data[i] = resp[i].count;
                    dataArrayFinal[i] = new Array(name[i],data[i]);
                }
                drawPie($("#chart_01"), '开行状态', dataArrayFinal, [
                    '#006400',
                    '#808080',
                    '#8b0000',
                    '#C800C8'
                ]);
            }

        }).fail(function() {

        }).always(function() {

        })

        $.ajax({
            url: "audit/plan/chart/planline/" + date,
            method: "GET",
            contentType: "application/json; charset=UTF-8"
        }).done(function(resp) {
            if(resp && resp.length) {
                var name = Array();
                var data = Array();
                var dataArrayFinal = Array();
                for(var i = 0; i < resp.length; i ++) {
                    name[i] = resp[i].name;
                    data[i] = resp[i].count;
                    dataArrayFinal[i] = new Array(name[i],data[i]);
                }
                drawPie($("#chart_02"), '上图状态', dataArrayFinal, [
                    '#006400',
                    '#8b0000',
                    '#C800C8'
                ]);
            }

        }).fail(function() {

        }).always(function() {

        });

        if($("#chart_03").size() == 1) {
            $.ajax({
                url: "audit/plan/chart/lev1check/" + date,
                method: "GET",
                contentType: "application/json; charset=UTF-8"
            }).done(function(resp) {
                if(resp && resp.length) {
                    var name = Array();
                    var data = Array();
                    var dataArrayFinal = Array();
                    for(var i = 0; i < resp.length; i ++) {
                        name[i] = resp[i].name;
                        data[i] = resp[i].count;
                        dataArrayFinal[i] = new Array(name[i],data[i]);
                    }
                    drawPie($("#chart_03"), '审核状态', dataArrayFinal, [
                        '#006400',
                        '#8b0000'
                    ]);
                }

            }).fail(function() {

            }).always(function() {

            })
        } else if($("#chart_04").size() == 1) {
            $.ajax({
                url: "audit/plan/chart/lev2check/" + date,
                method: "GET",
                contentType: "application/json; charset=UTF-8"
            }).done(function(resp) {
                if(resp && resp.length) {
                    var name = Array();
                    var data = Array();
                    var dataArrayFinal = Array();
                    for(var i = 0; i < resp.length; i ++) {
                        name[i] = resp[i].name;
                        data[i] = resp[i].count;
                        dataArrayFinal[i] = new Array(name[i],data[i]);
                    }
                    drawPie($("#chart_04"), '审核状态', dataArrayFinal, [
                        '#006400',
                        '#8b0000'
                    ]);
                }

            }).fail(function() {

            }).always(function() {

            })
        }

        $.ajax({
            url: "audit/check/line/" + $("#date_selector").val() + "/unknown",
            method: "GET",
            contentType: "application/json; charset=UTF-8"
        }).done(function(resp) {
            if(resp.code) {

            } else {
                self.unknownRunLine(resp.NUM);
            }

        }).fail(function() {

        }).always(function() {

        })
    }

    self.loadPies(moment($("#date_selector").val()).format("YYYYMMDD"));

}

// ################# 列表模型 #############
function TableModel() {
    var self = this;

    self.planList = ko.observableArray();

    self.loadTable = function() {
        commonJsScreenLock();
        var date = moment($("#date_selector").val()).format("YYYYMMDD");
        $.ajax({
            url: "audit/plan/runplan/" + date + "/1",
            method: "GET",
            contentType: "application/json; charset=UTF-8"
        }).done(function(list) {
            self.planList.removeAll();
            for( var i = 0; i < list.length; i++) {
                self.planList.push(new Plan(list[i]));
            }
            // 表头固定
            $("#plan_table").freezeHeader();
        }).fail(function() {

        }).always(function() {
            commonJsScreenUnLock();
        })
    };
}

function Plan(dto) {
    var self = this;

    // properties
    self.id = ko.observable(dto.id);
    self.name = ko.observable(dto.serial);
    self.startStn = ko.observable(dto.startSTN);
    self.startTime = ko.observable(dto.startTime);
    self.endStn = ko.observable(dto.endSTN);
    self.endTime = ko.observable(dto.endTime);
    self.sourceType = ko.observable(dto.sourceType);
    self.dailyLineFlag = ko.observable(dto.dailyLineFlag);
    self.dailyLineTime = ko.observable(dto.dailyLineTime);
    self.checkLev1 = ko.observable(dto.checkLev1);
    self.checkLev2 = ko.observable(dto.checkLev2);
    self.highLineFlag = ko.observable(dto.highLineFlag);
    self.dailyLineId = ko.observable(dto.dailyLineId);
    self.lev1Checked = ko.observable(dto.lev1Checked);
    self.lev2Checked = ko.observable(dto.lev2Checked);
    self.isSelected = ko.observable(false);

    // 校验项 0：未校验，1：匹配，-1：不匹配
    self.isTrainInfoMatch = ko.observable(0);
    self.isTimeTableMatch = ko.observable(0);
    self.isRoutingMatch = ko.observable(0);

    // computed
    self.isTrainInfoMatchClass = ko.computed(function() {
        var className = "btn-default";
        switch(self.isTrainInfoMatch()) {
            case 0:
                className = "btn-warning";
                break;
            case 1:
                className = "btn-info";
                break;
            case -1:
                className = "btn-danger";
                break;
            default:
        }
        return className;
    });

    self.isTrainInfoMatchText = ko.computed(function() {
        var txt = "未知";
        switch(self.isTrainInfoMatch()) {
            case 0:
                txt = "未校验";
                break;
            case 1:
                txt = "匹配";
                break;
            case -1:
                txt = "不匹配";
                break;
            default:
        }
        return txt;
    });

    self.isTimeTableMatchClass = ko.computed(function() {
        var className = "btn-default";
        switch(self.isTimeTableMatch()) {
            case 0:
                className = "btn-warning";
                break;
            case 1:
                className = "btn-info";
                break;
            case -1:
                className = "btn-danger";
                break;
            default:
        }
        return className;
    });

    self.isTimeTableMatchText = ko.computed(function() {
        var txt = "未知";
        switch(self.isTimeTableMatch()) {
            case 0:
                txt = "未校验";
                break;
            case 1:
                txt = "匹配";
                break;
            case -1:
                txt = "不匹配";
                break;
            default:
        }
        return txt;
    });

    self.isRoutingMatchClass = ko.computed(function() {
        var className = "btn-default";
        switch(self.isRoutingMatch()) {
            case 0:
                className = "btn-warning";
                break;
            case 1:
                className = "btn-info";
                break;
            case -1:
                className = "btn-danger";
                break;
            default:
        }
        return className;
    });

    self.isRoutingMatchText = ko.computed(function() {
        var txt = "未知";
        switch(self.isRoutingMatch()) {
            case 0:
                txt = "未校验";
                break;
            case 1:
                txt = "匹配";
                break;
            case -1:
                txt = "不匹配";
                break;
            default:
        }
        return txt;
    });

    self.needLev1 = ko.computed(function() {
        return self.lev1Checked() == 0;
    });

    self.needLev2 = ko.computed(function() {
        return self.checkLev1() == 2 && self.lev2Checked() == 0;
    });

    self._default = {
        autoOpen: false,
        height: $(window).height()/2,
        width: 800,
        title: "",
        position: [($(window).width() - 800), 0],
        maxWidth: $(window).width(),
        maxHeight: $(window).height(),
        model: true
    };

    self._getDialog = function(page, options) {
        var _default = {
            autoOpen: options.autoOpen || self._default.autoOpen,
            height: options.height || self._default.height,
            width: options.width || self._default.width,
            title: options.title || self._default.title,
            position: options.position || self._default.position,
            maxWidth: self._default.maxWidth,
            maxHeight: self._default.maxHeight,
            closeText: "关闭",
            model: true,
            close: options.close || null
        };
        var $dialog = $('<div class="dialog" style="overflow: hidden"></div>')
            .html('<iframe src="' + page + '" width="100%" height="100%" marginWidth=0 frameSpacing=0 marginHeight=0 scrolling=auto frameborder="0px"></iframe>')
            .dialog(_default);
        return $dialog;
    };

    // actions
    self.showInfoComparePanel = function() {
        var url = "audit/compare/traininfo/plan/" + self.id() + "/line/" + self.dailyLineId();
        self._getDialog(url, {title: "客运计划列车信息 vs 日计划列车信息", width: 850}).dialog("open");
    }

    self.showTimeTableComparePanel = function() {
        var url = "audit/compare/timetable/" + $("#bureau option:selected").val() + "/plan/" + self.id() + "/line/" + self.dailyLineId();
        self._getDialog(url, {title: "客运计划列车时刻表 vs 日计划列车时刻表", height: $(window).height(), width: 1300}).dialog("open");
    }
}


function getHintTitle(reqLength, respLength) {
    if(reqLength == respLength) {
        return "审核成功";
    } else if(reqLength > respLength) {
        return "审核部分成功";
    } else {
        return "审核出错";
    }
}

function getHintCss(reqLength, respLength) {
    if(reqLength == respLength) {
        return "growl-success";
    } else if(reqLength > respLength) {
        return "growl-warning";
    } else {
        return "growl-danger";
    }
}

function drawPie($div, chartName, data, colors) {
    $div.highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: chartName
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.y}</b>',
            percentageDecimals: 1
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false
                },
                showInLegend: true
            }
        },
        colors:colors,
        series: [{
            type: 'pie',
            name: '列车数量',
            data: data
        }]
    });
}