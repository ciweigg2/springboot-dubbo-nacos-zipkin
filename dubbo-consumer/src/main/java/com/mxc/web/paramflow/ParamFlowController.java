package com.mxc.web.paramflow;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2019/1/20.
 *
 * 热点限流接口测试
 */
@RestController
public class ParamFlowController {

	private final String resourceName = "ParamFlowA";

	@GetMapping("/freqParamFlow")
	public @ResponseBody String freqParamFlow(@RequestParam("uid") int uid, @RequestParam("ip") int ip) {
		Entry entry = null;
		String retVal;
		try{
			// 只对参数uid进行限流，参数ip不进行限制 如果配置了uid,ip需要配置索引位置是1才能使用ip限流的
			entry = SphU.entry(resourceName, EntryType.IN,1,uid);
			retVal = "passed";
		}catch(BlockException e){
			retVal = "blocked";
		}finally {
			if(entry!=null){
				entry.exit();
			}
		}
		return retVal;
	}

}
