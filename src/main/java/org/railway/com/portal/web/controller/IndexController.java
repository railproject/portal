package org.railway.com.portal.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.railway.com.portal.service.RunLineService;
import org.railway.com.portal.service.RunPlanService;
import org.railway.com.portal.web.dto.PlanLineInfoDto;
import org.railway.com.portal.web.dto.PlanLineSTNDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by star on 5/12/14.
 */
@Controller
@RequestMapping(value = "/")
public class IndexController {

    private final static Log logger = LogFactory.getLog(IndexController.class);

    @Autowired
    private RunPlanService runPlanService;

    @Autowired
    private RunLineService runLineService;

    @RequestMapping(value = "audit", method = RequestMethod.GET)
    public String audit() {
        return "portal/planline_check";
    }

    @RequestMapping(value = "audit/compare/traininfo/plan/{planId}/line/{lineId}", method = RequestMethod.GET)
    public ModelAndView compareTrainInfo(@PathVariable String planId, @PathVariable String lineId, ModelAndView modelAndView) {
        logger.info("compareTrainInfo##################");
        // 查询客运计划主体信息
        Map<String, Object> plan = runPlanService.findPlanInfoByPlanId(planId);
        PlanLineInfoDto planDto = new PlanLineInfoDto(plan);
        modelAndView.addObject("plan", planDto);
        // 查询日计划主体信息
        Map<String, Object> line = runLineService.findLineInfoByLineId(lineId);
        PlanLineInfoDto lineDto = new PlanLineInfoDto(line);
        modelAndView.addObject("line", lineDto);

        // 添加跳转信息
        modelAndView.setViewName("portal/compare_traininfo");
        return modelAndView;
    }

    /**
     * 比较客运计划和日计划时刻表
     * @param bureau
     * @param planId
     * @param lineId
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "audit/compare/timetable/{bureau}/plan/{planId}/line/{lineId}", method = RequestMethod.GET)
    public ModelAndView compareTimetable(@PathVariable String bureau, @PathVariable String planId,
                                  @PathVariable String lineId, ModelAndView modelAndView) {
        logger.info("compareTimetable###################");
        modelAndView.setViewName("portal/compare_timetable");
        // 取客运计划时刻表
        List<PlanLineSTNDto> planList = new ArrayList<PlanLineSTNDto>();
        List<Map<String, Object>> plans = runPlanService.findPlanTimeTableByPlanId(planId);
        for(Map<String, Object> map: plans) {
            planList.add(new PlanLineSTNDto(map, bureau));
        }
        modelAndView.addObject("planList", planList);
        // 取日计划时刻表
        List<PlanLineSTNDto> lineList = new ArrayList<PlanLineSTNDto>();
        List<Map<String, Object>> lines = runLineService.findLineTimeTableByLineId(lineId);
        for(Map<String, Object> map: lines) {
            lineList.add(new PlanLineSTNDto(map, bureau));
        }
        modelAndView.addObject("lineList", lineList);
        return modelAndView;
    }

    @RequestMapping(value = "audit/plan/routing", method = RequestMethod.GET)
    public String routing() {
        return "portal/routing";
    }


    @RequestMapping(value = "audit/planline", method = RequestMethod.GET)
    public ModelAndView graphic(@RequestParam(value = "date") String date, @RequestParam(value = "bureau") String bureau,
                          @RequestParam(value = "plans", defaultValue = "") String plans,
                          @RequestParam(value = "lines", defaultValue = "") String lines,
                          ModelAndView modelAndView) throws JsonProcessingException {
        modelAndView.setViewName("portal/planline");
        /*if(plans.endsWith(",")) {
            plans = plans.substring(0, plans.length() - 1);
        }
        if(lines.endsWith(",")) {
            lines = lines.substring(0, lines.length() - 1);
        }
        // runplan
        ObjectMapper objectMapper = new ObjectMapper();
        List<PlanLineDTO> runplan = new ArrayList<PlanLineDTO>();
        List<PlanLineGridX> gridXList = new ArrayList<PlanLineGridX>();
        List<PlanLineGridY> planLineGridYList = new ArrayList<PlanLineGridY>();
        if(plans.length() > 0) {
            List<Map<String, Object>> planList = runPlanService.findRunPlans(plans);
            List<Map<String, Object>> planLineY = runPlanService.findPlanLineSTNs(plans);
            List<String> startList = new ArrayList<String>();
            List<java.sql.Timestamp> endList = new ArrayList<java.sql.Timestamp>();
            PlanLineDTO planLineDTO = null;
            for(int i = 0; i < planList.size(); i++) {
                Map<String, Object> row = planList.get(i);
                if(planLineDTO == null || !planLineDTO.getTrainName().equals(MapUtils.getString(row, "TRAIN_NBR"))) {
                    planLineDTO = new PlanLineDTO(planList.get(i));
                    runplan.add(planLineDTO);
                    startList.add(MapUtils.getString(row, "RUN_DATE"));
                }
                PlanLineSTNDTO planLineSTNDTO = new PlanLineSTNDTO(row);
                planLineDTO.getTrainStns().add(planLineSTNDTO);
                endList.add((java.sql.Timestamp) row.get("ARR_TIME"));
                endList.add((java.sql.Timestamp) row.get("DPT_TIME"));
            }

            // Grid

            for(Map<String, Object> map: planLineY) {
                planLineGridYList.add(new PlanLineGridY(MapUtils.getString(map, "STN_NAME")));
            }

            Collections.sort(startList, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    LocalDate d1 = DateTimeFormat.forPattern("yyyyMMdd").parseLocalDate(o1);
                    LocalDate d2 = DateTimeFormat.forPattern("yyyyMMdd").parseLocalDate(o2);
                    return d1.compareTo(d2);
                }
            });

            Collections.sort(endList, new Comparator<java.sql.Timestamp>() {
                @Override
                public int compare(java.sql.Timestamp o1, java.sql.Timestamp o2) {
                    return o1.compareTo(o2);
                }
            });
            LocalDate ss = DateTimeFormat.forPattern("yyyyMMdd").parseLocalDate(startList.get(0));
            Date eee = new Date(endList.get(endList.size() - 1).getTime());
            LocalDate ee = new LocalDate(eee);

            while(!ss.isAfter(ee)) {
                gridXList.add(new PlanLineGridX(ss.toString("yyyy-MM-dd")));
                ss = ss.plusDays(1);
            }
        }
        String runplanStr = objectMapper.writeValueAsString(runplan);
        logger.debug("runplan: " + runplanStr);
        modelAndView.addObject("runplan", runplanStr);
*/
        /*
        // runline
        List<PlanLineDTO> runline = new ArrayList<PlanLineDTO>();
        if(lines.length() > 0) {
            String[] line_ids = lines.split(",");
            for(String line_id: line_ids) {
                Map<String, Object> l1 =runLineService.getRunLineSTN(line_id);
                PlanLineDTO pld = new PlanLineDTO();
                runline.add(pld);
                pld.setTrainName(MapUtils.getString(l1, "name", ""));
                pld.setStartStn(MapUtils.getString(l1, "sourceNodeName", ""));
                pld.setEndStn(MapUtils.getString(l1, "targetNodeName", ""));
                List<PlanLineSTNDTO> trainStns = new ArrayList<PlanLineSTNDTO>();
                pld.setTrainStns(trainStns);

                List<Map<String, Object>> lineStn = new ArrayList<Map<String, Object>>();
                Map<String, Map<String, Object>> timeTable = (Map<String, Map<String, Object>>) l1.get("scheduleDto");
                lineStn.add(timeTable.get("sourceItemDto"));
                List<Map<String, Object>> routingTable = (List<Map<String, Object>>) timeTable.get("routeItemDtos");
                lineStn.addAll(routingTable);
                lineStn.add(timeTable.get("targetItemDto"));
                for(Map<String, Object> tmp: lineStn) {
                    if(tmp != null) {
                        PlanLineSTNDTO planLineSTNDTO = new PlanLineSTNDTO(tmp, 1);
                        trainStns.add(planLineSTNDTO);
                    }
                }
            }
        }
        String runlineStr = objectMapper.writeValueAsString(runline);
        logger.debug("runline: " + runlineStr);
        modelAndView.addObject("runline", runlineStr);



        PlanLineGrid grid = new PlanLineGrid(gridXList, planLineGridYList);
        String gridStr = objectMapper.writeValueAsString(grid);
        logger.debug("grid: " + gridStr);
        modelAndView.addObject("grid", gridStr);
*/

        return modelAndView;
    }
}
