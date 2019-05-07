package com.admin.railway.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.admin.common.config.ApplicationContextRegister;
import com.admin.common.config.Constant;
import com.admin.common.utils.R;
import com.admin.common.utils.ShiroUtils;
import com.admin.common.utils.StringUtils;
import com.admin.railway.service.ApiService;
import com.admin.system.domain.UserDO;
import com.alibaba.fastjson.JSON;

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
			ApiService apiService = ApplicationContextRegister.getBean(ApiService.class);
			String token = req.getHeader(Constant.TOKEN);
			//验证App接口
			if(StringUtils.isNotBlank(token)){
				R r = apiService.getToken(token);
				int code = Integer.valueOf(r.get("code").toString());
				if(Constant.Number.ZERO.getCode() == code){
					filterChain.doFilter(request, response);
				}else {
					PrintWriter writer = rep.getWriter();
					String json = JSON.toJSONString(r);
					writer.write(json);
					writer.flush();
					writer.close();
				}
			}else {
				PrintWriter writer = rep.getWriter();
				String json = JSON.toJSONString(R.error(Constant.ErrorInfo.AUTH_FAIL.getCode(),Constant.ErrorInfo.AUTH_FAIL.getMsg()));
				writer.write(json);
				writer.flush();
				writer.close();
			}
		}else {
			rep.setHeader("Access-Control-Allow-origin", "http://127.0.0.1:8848");
			rep.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
			rep.setHeader("Access-Control-Max-Age", "0");
			rep.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
			rep.setHeader("Access-Control-Allow-Credentials", "true");
			rep.setHeader("XDomainRequestAllowed","1");
			filterChain.doFilter(request, rep);
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
