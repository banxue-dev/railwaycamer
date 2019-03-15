package com.admin.railway.filter;

import com.admin.common.config.ApplicationContextRegister;
import com.admin.common.config.Constant;
import com.admin.common.utils.R;
import com.admin.common.utils.StringUtils;
import com.admin.railway.service.ApiService;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Lj
 */
@Component
public class ApiFilter implements Filter {

    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rep = (HttpServletResponse) response;
		String requestUrl = req.getRequestURI();
  		if(this.isUrl(requestUrl)){
			rep.setContentType("application/json; charset=utf-8");
			rep.setCharacterEncoding("UTF-8");
			PrintWriter writer = rep.getWriter();
			ApiService apiService = ApplicationContextRegister.getBean(ApiService.class);
			String token = req.getHeader(Constant.TOKEN);
			//验证App接口
			if(StringUtils.isNotBlank(token)){
				R r = apiService.getToken(token);
				int code = Integer.valueOf(r.get("code").toString());
				if(Constant.Number.ZERO.getCode() == code){
					filterChain.doFilter(request, response);
				}else {
					String json = JSON.toJSONString(r);
					writer.write(json);
				}
			}else {
				String json = JSON.toJSONString(R.error(Constant.ErrorInfo.AUTH_FAIL.getCode(),Constant.ErrorInfo.AUTH_FAIL.getMsg()));
				writer.write(json);
			}
			writer.flush();
			writer.close();
		}else {
			filterChain.doFilter(request, response);
		}

    }

    private boolean isUrl(String requestUrl){
		String [] urlArry = {"/api/uploadImg","/api/listTask","/api/updatePassword"};
		for(String url : urlArry){
			if(requestUrl.indexOf(url) != -1){
				return true;
			}
		}
		return false;
	}

	@Override
	public void init(FilterConfig filterConfig) {}

	@Override
	public void destroy() {}  
  
}  
