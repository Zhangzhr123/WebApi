package com.authine.cloudpivot.mybatis.controller;

import com.authine.cloudpivot.mybatis.entity.*;
import com.authine.cloudpivot.mybatis.mapper.DEMOMapper;
import com.authine.cloudpivot.mybatis.utils.FileUtil;
import com.authine.cloudpivot.mybatis.utils.POIUtil;
import com.authine.cloudpivot.web.api.controller.base.BaseController;
import com.authine.cloudpivot.web.api.handler.CustomizedOrigin;
import com.authine.cloudpivot.web.api.view.ResponseResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Api(value = "Demo", tags = "Demo")
@RestController
@RequestMapping("/public/api/demo")
public class BGWordDomeController extends BaseController {

    @Autowired
    DEMOMapper demoMapper;

    @ResponseBody
    @GetMapping("/getDEMO")
    @CustomizedOrigin(level = 5)
    public ResponseResult<String> getDEMO(String id, HttpServletResponse resp, HttpServletRequest req) {

        //模板文件地址
        String inputUrl = "/doc/半钢PS2A半钢一次法成型机技术协议-模板.docx";
        String outputUrl = "";
        try {
            //获取文件路径
            File directory = new File("");//参数为空
            inputUrl = directory.getCanonicalPath() + inputUrl;
            System.out.println(inputUrl);//a85309e8c8e24f6e8a59406be8f554cf
            //我要获取当前的日期
            Date date = new Date();
            //设置要获取到什么样的时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            //获取String类型的时间
            String createdate = sdf.format(date);
            //输出地址
            outputUrl = "/doc/成型机技术协议[" + createdate + "].docx";
            outputUrl = directory.getCanonicalPath() + outputUrl;
            //判断文件格式
            if (inputUrl.endsWith(".docx")) {

                //获取流程数据
                DEMOEntity demo = demoMapper.getDEMO(id);
                System.out.println(demo);

                //插入数据所需
                Map<String, String> testMap = new HashMap<String, String>();
                Map<String, List<String[]>> testMap2 = new HashMap<String, List<String[]>>();

                if (demo != null) {

                    //替换内容
                    testMap.put("BT", demo.getBT());
                    testMap.put("RQ", demo.getRQ());
                    testMap.put("VERSION", demo.getVERSION());
                    testMap.put("XGSM", demo.getXGSM());
                    testMap.put("BZ", demo.getBZ());
                    testMap.put("PZ", demo.getPZ());
                    testMap.put("MF", demo.getMF());
                    testMap.put("SJ", demo.getSJ());
                    testMap.put("FM", demo.getFM());
                    testMap.put("CP", demo.getCP());
                    testMap.put("KFHSCDD", demo.getKFHSCDD());
                    testMap.put("FHSCDD", demo.getFHSCDD());
                    testMap.put("PXDD", demo.getPXDD());
                    testMap.put("PXSJ", demo.getPXSJ());
                    testMap.put("YSBZ", demo.getYSBZ());
                    testMap.put("SCZQ", demo.getSCZQ());
                    testMap.put("CLCSNL", demo.getCLCSNL());
                    testMap.put("BZQSJ1", demo.getBZQSJ1());
                    testMap.put("BZQSJ2", demo.getBZQSJ2());
                    testMap.put("MFJSZL", demo.getMFJSZL());
                    testMap.put("LXFSGSMC", demo.getLXFSGSMC());
                    testMap.put("LXFSGSDD", demo.getLXFSGSDD());
                    testMap.put("LXFSDH", demo.getLXFSDH());
                    testMap.put("LXFSYX", demo.getLXFSYX());
                    testMap.put("ZJGS", demo.getZJGS());
                    testMap.put("ZJRQ", demo.getZJRQ());
                    testMap.put("MFGS", demo.getMFGS());
                    testMap.put("MFRQ", demo.getMFRQ());

                    //获取供货范围清单
                    List<DEMO1Entity> demo1 = demoMapper.getDEMO1(demo.getId());
                    System.out.println(demo1);
                    List<String[]> testList1 = new ArrayList<String[]>();
                    //插入内容
                    if (demo1 != null && demo1.size() > 0) {
                        //循环插入字标内容
                        for (int i = 0; i < demo1.size(); i++) {
                            testList1.add(new String[]{(i+1)+"",demo1.get(i).getMC(),demo1.get(i).getSL(),demo1.get(i).getBZ()});
                        }
                    }
                    testMap2.put("GHFWQD", testList1);

                    //获取关键元器件清单
                    List<DEMO4Entity> demo4 = demoMapper.getDEMO4(demo.getId());
                    System.out.println(demo4);
                    List<String[]> testList4 = new ArrayList<String[]>();
                    //插入内容
                    if (demo4 != null && demo4.size() > 0) {
                        //循环插入字标内容
                        for (int i = 0; i < demo4.size(); i++) {
                            testList4.add(new String[]{demo4.get(i).getMC(),demo4.get(i).getGYS()});
                        }
                    }
                    testMap2.put("GJYQJQD", testList4);

                    //获取BCPBJJDDSC
                    List<DEMO2Entity> demo2_1 = demoMapper.getDEMO2_1(demo.getId());
                    System.out.println(demo2_1);
                    List<String[]> testList2_1 = new ArrayList<String[]>();
                    //插入内容
                    if (demo2_1 != null && demo2_1.size() > 0) {
                        //循环插入字标内容
                        for (int i = 0; i < demo2_1.size(); i++) {
                            testList2_1.add(new String[]{demo2_1.get(i).getMC(),demo2_1.get(i).getYQ(),demo2_1.get(i).getBZ()});
                        }
                    }
                    testMap2.put("BCPBJJDDSC", testList2_1);

                    //获取BCPBJJDLB
                    List<DEMO2Entity> demo2_2 = demoMapper.getDEMO2_2(demo.getId());
                    System.out.println(demo2_2);
                    List<String[]> testList2_2 = new ArrayList<String[]>();
                    //插入内容
                    if (demo2_2 != null && demo2_2.size() > 0) {
                        //循环插入字标内容
                        for (int i = 0; i < demo2_2.size(); i++) {
                            testList2_2.add(new String[]{demo2_2.get(i).getMC(),demo2_2.get(i).getYQ(),demo2_2.get(i).getBZ()});
                        }
                    }
                    testMap2.put("BCPBJJDLB", testList2_2);

                    //获取BCPBJJDNCC
                    List<DEMO2Entity> demo2_3 = demoMapper.getDEMO2_3(demo.getId());
                    System.out.println(demo2_3);
                    List<String[]> testList2_3 = new ArrayList<String[]>();
                    //插入内容
                    if (demo2_3 != null && demo2_3.size() > 0) {
                        //循环插入字标内容
                        for (int i = 0; i < demo2_3.size(); i++) {
                            testList2_3.add(new String[]{demo2_3.get(i).getMC(),demo2_3.get(i).getYQ(),demo2_3.get(i).getBZ()});
                        }
                    }
                    testMap2.put("BCPBJJDNCC", testList2_3);

                    //获取BCPBJJDTC
                    List<DEMO2Entity> demo2_4 = demoMapper.getDEMO2_4(demo.getId());
                    System.out.println(demo2_4);
                    List<String[]> testList2_4 = new ArrayList<String[]>();
                    //插入内容
                    if (demo2_4 != null && demo2_4.size() > 0) {
                        //循环插入字标内容
                        for (int i = 0; i < demo2_4.size(); i++) {
                            testList2_4.add(new String[]{demo2_4.get(i).getMC(),demo2_4.get(i).getYQ(),demo2_4.get(i).getBZ()});
                        }
                    }
                    testMap2.put("BCPBJJDTC", testList2_4);

                    //获取BCPBJJDTM
                    List<DEMO2Entity> demo2_5 = demoMapper.getDEMO2_5(demo.getId());
                    System.out.println(demo2_5);
                    List<String[]> testList2_5 = new ArrayList<String[]>();
                    //插入内容
                    if (demo2_5 != null && demo2_1.size() > 0) {
                        //循环插入字标内容
                        for (int i = 0; i < demo2_5.size(); i++) {
                            testList2_5.add(new String[]{demo2_5.get(i).getMC(),demo2_5.get(i).getYQ(),demo2_5.get(i).getBZ()});
                        }
                    }
                    testMap2.put("BCPBJJDTM", testList2_5);

                    //获取BCPBJJDTQ
                    List<DEMO2Entity> demo2_6 = demoMapper.getDEMO2_6(demo.getId());
                    System.out.println(demo2_6);
                    List<String[]> testList2_6 = new ArrayList<String[]>();
                    //插入内容
                    if (demo2_6 != null && demo2_6.size() > 0) {
                        //循环插入字标内容
                        for (int i = 0; i < demo2_6.size(); i++) {
                            testList2_6.add(new String[]{demo2_6.get(i).getMC(),demo2_6.get(i).getYQ(),demo2_6.get(i).getBZ()});
                        }
                    }
                    testMap2.put("BCPBJJDTQ", testList2_6);

                    //获取DLJDDSC
                    List<DEMO2Entity> demo2_7 = demoMapper.getDEMO2_7(demo.getId());
                    System.out.println(demo2_7);
                    List<String[]> testList2_7 = new ArrayList<String[]>();
                    //插入内容
                    if (demo2_7 != null && demo2_7.size() > 0) {
                        //循环插入字标内容
                        for (int i = 0; i < demo2_7.size(); i++) {
                            testList2_7.add(new String[]{demo2_7.get(i).getMC(),demo2_7.get(i).getYQ(),demo2_7.get(i).getBZ()});
                        }
                    }
                    testMap2.put("DLJDDSC", testList2_7);

                    //获取DLJDGDT
                    List<DEMO2Entity> demo2_8 = demoMapper.getDEMO2_8(demo.getId());
                    System.out.println(demo2_8);
                    List<String[]> testList2_8 = new ArrayList<String[]>();
                    //插入内容
                    if (demo2_8 != null && demo2_8.size() > 0) {
                        //循环插入字标内容
                        for (int i = 0; i < demo2_8.size(); i++) {
                            testList2_8.add(new String[]{demo2_8.get(i).getMC(),demo2_8.get(i).getYQ(),demo2_8.get(i).getBZ()});
                        }
                    }
                    testMap2.put("DLJDGDT", testList2_8);

                    //获取DLJDLB
                    List<DEMO2Entity> demo2_9 = demoMapper.getDEMO2_9(demo.getId());
                    System.out.println(demo2_9);
                    List<String[]> testList2_9 = new ArrayList<String[]>();
                    //插入内容
                    if (demo2_9 != null && demo2_9.size() > 0) {
                        //循环插入字标内容
                        for (int i = 0; i < demo2_9.size(); i++) {
                            testList2_9.add(new String[]{demo2_9.get(i).getMC(),demo2_9.get(i).getYQ(),demo2_9.get(i).getBZ()});
                        }
                    }
                    testMap2.put("DLJDLB", testList2_9);

                    //获取DLJDNCCHTC
                    List<DEMO2Entity> demo2_10 = demoMapper.getDEMO2_10(demo.getId());
                    System.out.println(demo2_10);
                    List<String[]> testList2_10 = new ArrayList<String[]>();
                    //插入内容
                    if (demo2_10 != null && demo2_10.size() > 0) {
                        //循环插入字标内容
                        for (int i = 0; i < demo2_10.size(); i++) {
                            testList2_10.add(new String[]{demo2_10.get(i).getMC(),demo2_10.get(i).getYQ(),demo2_10.get(i).getBZ()});
                        }
                    }
                    testMap2.put("DLJDNCCHTC", testList2_10);

                    //获取DLJDTM
                    List<DEMO2Entity> demo2_11 = demoMapper.getDEMO2_11(demo.getId());
                    System.out.println(demo2_11);
                    List<String[]> testList2_11 = new ArrayList<String[]>();
                    //插入内容
                    if (demo2_11 != null && demo2_11.size() > 0) {
                        //循环插入字标内容
                        for (int i = 0; i < demo2_11.size(); i++) {
                            testList2_11.add(new String[]{demo2_11.get(i).getMC(),demo2_11.get(i).getYQ(),demo2_11.get(i).getBZ()});
                        }
                    }
                    testMap2.put("DLJDTM", testList2_11);

                    //获取KCJD
                    List<DEMO2Entity> demo2_12 = demoMapper.getDEMO2_12(demo.getId());
                    System.out.println(demo2_12);
                    List<String[]> testList2_12 = new ArrayList<String[]>();
                    //插入内容
                    if (demo2_12 != null && demo2_12.size() > 0) {
                        //循环插入字标内容
                        for (int i = 0; i < demo2_12.size(); i++) {
                            testList2_12.add(new String[]{(i+1)+"",demo2_12.get(i).getMC(),demo2_12.get(i).getYQ(),demo2_12.get(i).getBZ()});
                        }
                    }
                    testMap2.put("KCJD", testList2_12);

                    //获取DSCGLJ
                    List<DEMO3Entity> demo3_1 = demoMapper.getDEMO3_1(demo.getId());
                    System.out.println(demo3_1);
                    List<String[]> testList3_1 = new ArrayList<String[]>();
                    //插入内容
                    if (demo3_1 != null && demo3_1.size() > 0) {
                        //循环插入字标内容
                        for (int i = 0; i < demo3_1.size(); i++) {
                            testList3_1.add(new String[]{(i+1)+"",demo3_1.get(i).getMC(),demo3_1.get(i).getJSCS(),demo3_1.get(i).getBZ()});
                        }
                    }
                    testMap2.put("DSCGLJ", testList3_1);

                    //获取GDTGLJ
                    List<DEMO3Entity> demo3_2 = demoMapper.getDEMO3_2(demo.getId());
                    System.out.println(demo3_2);
                    List<String[]> testList3_2 = new ArrayList<String[]>();
                    //插入内容
                    if (demo3_2 != null && demo3_2.size() > 0) {
                        //循环插入字标内容
                        for (int i = 0; i < demo3_2.size(); i++) {
                            testList3_2.add(new String[]{(i+1)+"",demo3_2.get(i).getMC(),demo3_2.get(i).getJSCS(),demo3_2.get(i).getBZ()});
                        }
                    }
                    testMap2.put("GDTGLJ", testList3_2);

                    //获取TMCGLJ
                    List<DEMO3Entity> demo3_3 = demoMapper.getDEMO3_3(demo.getId());
                    System.out.println(demo3_3);
                    List<String[]> testList3_3 = new ArrayList<String[]>();
                    //插入内容
                    if (demo3_3 != null && demo3_3.size() > 0) {
                        //循环插入字标内容
                        for (int i = 0; i < demo3_3.size(); i++) {
                            testList3_1.add(new String[]{(i+1)+"",demo3_3.get(i).getMC(),demo3_3.get(i).getJSCS(),demo3_3.get(i).getBZ()});
                        }
                    }
                    testMap2.put("TMCGLJ", testList3_3);

                    //获取ZGLJ
                    List<DEMO3Entity> demo3_4 = demoMapper.getDEMO3_4(demo.getId());
                    System.out.println(demo3_4);
                    List<String[]> testList3_4 = new ArrayList<String[]>();
                    //插入内容
                    if (demo3_4 != null && demo3_4.size() > 0) {
                        //循环插入字标内容
                        for (int i = 0; i < demo3_4.size(); i++) {
                            testList3_4.add(new String[]{(i+1)+"",demo3_4.get(i).getMC(),demo3_4.get(i).getJSCS(),demo3_4.get(i).getBZ()});
                        }
                    }
                    testMap2.put("ZGLJ", testList3_4);

                    //获取ZJ
                    List<DEMO3Entity> demo3_5 = demoMapper.getDEMO3_5(demo.getId());
                    System.out.println(demo3_5);
                    List<String[]> testList3_5 = new ArrayList<String[]>();
                    //插入内容
                    if (demo3_5 != null && demo3_5.size() > 0) {
                        //循环插入字标内容
                        for (int i = 0; i < demo3_5.size(); i++) {
                            testList3_5.add(new String[]{(i+1)+"",demo3_5.get(i).getMC(),demo3_5.get(i).getJSCS(),demo3_5.get(i).getBZ()});
                        }
                    }
                    testMap2.put("ZJ", testList3_5);

                }


                /**
                 * 根据模板生成新word文档
                 * 判断表格是需要替换还是需要插入
                 * 判断逻辑根据${Key} Key获取Map的Value值类型进行判断替换还是插入
                 *
                 * @param inputUrl  模板存放地址
                 * @param outputUrl 新文档存放地址
                 * @param testMap   需要替换的信息集合
                 * @param testMap2 需要插入的表格信息集合
                 */
                POIUtil.changWord(inputUrl, outputUrl, testMap, testMap2);

                /**
                 * 下载新word文档
                 * 第一个参数为下载地址
                 * 第二个参数为文档名称
                 * 第三个和第四个参数为固定的response和request
                 */
                FileUtil.downloadFile(outputUrl, "成型机技术协议[" + createdate + "].docx", resp, req);

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
