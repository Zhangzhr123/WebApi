package com.authine.cloudpivot.mybatis.controller;

import com.authine.cloudpivot.mybatis.entity.LCBEntity;
import com.authine.cloudpivot.mybatis.entity.POIEntity;
import com.authine.cloudpivot.mybatis.mapper.POIMapper;
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
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Api(value = "POI接口", tags = "POI接口")
@RestController
@RequestMapping("/public/api/poi")
public class POIController extends BaseController {

    @Autowired
    POIMapper poiMapper;

    @ResponseBody
    @GetMapping("/getWord")
    @CustomizedOrigin(level = 5)
    public ResponseResult<String> GetDate(String id, HttpServletResponse resp, HttpServletRequest req) {

        //模板文件地址
        String inputUrl = "/doc/委托软件服务合同-模板.docx";
        String outputUrl = "";
        try {
            //获取文件路径
            File directory = new File("");//参数为空
            inputUrl = directory.getCanonicalPath() + inputUrl;
            System.out.println(inputUrl);
            //我要获取当前的日期
            Date date = new Date();
            //设置要获取到什么样的时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            //获取String类型的时间
            String createdate = sdf.format(date);
            //String fileName = "【海川信息】委托软件服务合同[" + createdate + "].doc";
            //输出地址
            outputUrl = "/doc/委托软件服务合同[" + createdate + "].docx";
            outputUrl = directory.getCanonicalPath() + outputUrl;
            //判断文件格式
            if (inputUrl.endsWith(".docx")) {

                //获取流程数据
                POIEntity poi = poiMapper.getPOI(id);
                System.out.println(poi);

                Map<String, String> testMap = new HashMap<String, String>();
                Map<String, List<String[]>> testMap2 = new HashMap<String, List<String[]>>();
                List<String[]> testList = new ArrayList<String[]>();

                if (poi != null) {
                    //查询子表
                    List<LCBEntity> lcb = poiMapper.getLCB(poi.getId());
                    System.out.println(lcb);

                    //替换内容
                    testMap.put("clf", poi.getClj());
                    testMap.put("dz", poi.getDz());
                    testMap.put("yb", poi.getYb());
                    testMap.put("fddbr", poi.getFddbr());
                    testMap.put("xmdd", poi.getXmdd());
                    testMap.put("qx", poi.getQx());
                    testMap.put("dwmc", poi.getDwmc());
                    testMap.put("sh", poi.getSh());
                    testMap.put("zcd", poi.getZcd());
                    testMap.put("khh", poi.getKhh());
                    testMap.put("yhzh", poi.getYhzh());
                    testMap.put("qtyq", poi.getQtyq());
                    testMap.put("zpfuxm", poi.getZpfuxm());
                    testMap.put("zprjfwnr", poi.getZprjfwnr());
                    testMap.put("zpyhs", poi.getZpyhs());
                    testMap.put("zpfwqx", poi.getZpfwqx());
                    testMap.put("zphtjg", poi.getZphtjg());
                    testMap.put("zpbz", poi.getZpbz());
                    testMap.put("zzxmmc", poi.getZzxmmc());
                    testMap.put("zzrjfwnr", poi.getZzrjfwnr());
                    testMap.put("zzsl", poi.getZzsl());
                    testMap.put("zzhtjg", poi.getZzhtjg());
                    testMap.put("zzbz", poi.getZzbz());

                    //插入内容
                    if (lcb != null && lcb.size() > 0) {
                        //循环插入字标内容
                        for (int i = 0; i < lcb.size(); i++) {
                            testList.add(new String[]{lcb.get(i).getLcbmc(), lcb.get(i).getFktj(), lcb.get(i).getFkbl(), lcb.get(i).getJe()});
                        }
                    }
                    testMap2.put("table1", testList);

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
                FileUtil.downloadFile(outputUrl, "委托软件服务合同[" + createdate + "].docx", resp, req);

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
