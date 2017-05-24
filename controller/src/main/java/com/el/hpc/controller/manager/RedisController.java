package com.el.hpc.controller.manager;

import com.el.hpc.service.IRedisService;
import com.el.hpc.service.RedisService;
import com.el.hpc.vo.RedisResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * 　　　　　　　　┏┓　　　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　　　 ████━████ ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 *
 * @User : Hapic
 * @Date : 2017/5/23 13:21
 * 操作redis的Controller
 */
@Controller
@RequestMapping("/manager/redis")
public class RedisController {


    @Autowired
    private RedisService redisService;

    @RequestMapping("page")
    public ModelAndView page() {
        return new ModelAndView("manager/redis");
    }

    @RequestMapping("do")
    @ResponseBody
    public RedisResultVo doCmd(RedisResultVo vo){
        if("get".equals(vo.getCmd())){
            String value = redisService.get(vo, null);
            if(value==null){
                value="查询不到";
            }
            vo.addValue("Key: "+vo.getKey(),"Value: "+value);

        }else if("ttl".equals(vo.getCmd())){
            String ttl = redisService.ttl(vo, null);
            if("-2".equals(ttl)){
                ttl="key不存在";
            }else if("-1".equals(ttl)){
                ttl="没有设置过去时间";
            }
            vo.addValue(ttl);
        }else if("exists".equals(vo.getCmd())){
            String exists = redisService.exists(vo, null);
            if("true".equals(exists)){
                exists="存在";
            }else
                exists="不存在";
            vo.addValue(exists);
        }else if("type".equals(vo.getCmd())){
            String type = redisService.type(vo, null);
            if(type==null){
                type="不存在";
            }
            vo.addValue(type);
        }else if("set".equals(vo.getCmd())){
            String type=null;
            if(vo.getValueStr()==null || vo.getValueStr().equals("")){

            }else {
                type= redisService.set(vo, null);
            }
            if(type==null){
                type="不存在";
            }

            vo.addValue(type);
        }else if("hget".equals(vo.getCmd())){
            List<String> hmget = redisService.hmget(vo, null);
            String field = vo.getField();
            String[] fields = field.split(",");

            for(int i=0;i<fields.length;i++){
                vo.addValue("Field: "+fields[i],"Value: "+hmget.get(i));
            }
        }else if("hset".equals(vo.getCmd())){
            Long hset = redisService.hset(vo, null);


        }

        return vo;
    }



}