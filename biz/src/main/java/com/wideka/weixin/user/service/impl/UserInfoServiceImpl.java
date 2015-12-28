package com.wideka.weixin.user.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.wideka.club.framework.util.HttpUtil;
import com.wideka.weixin.api.user.IUserInfoService;
import com.wideka.weixin.api.user.bo.User;
import com.wideka.weixin.framework.bo.Result;

/**
 * 
 * @author JiakunXu
 * 
 */
public class UserInfoServiceImpl implements IUserInfoService {

	private static Logger logger = Logger.getLogger(UserInfoServiceImpl.class);

	@Override
	public User getUserInfo(String accessToken, String code) throws RuntimeException {
		if (StringUtils.isBlank(accessToken)) {
			throw new RuntimeException("access_token cannot be null.");
		}

		if (StringUtils.isBlank(code)) {
			throw new RuntimeException("code cannot be null.");
		}

		User user = null;

		try {
			user =
				JSON.parseObject(HttpUtil.get(IUserInfoService.HTTPS_USER_INFO_URL.replace("$ACCESS_TOKEN$",
					accessToken.trim()).replace("$CODE$", code.trim())), User.class);
		} catch (Exception e) {
			logger.error(accessToken + "&" + code, e);

			throw new RuntimeException("HttpUtil error.", e);
		}

		if (user == null) {
			throw new RuntimeException("user is null.");
		}

		String errCode = user.getErrCode();
		if (StringUtils.isNotBlank(errCode) && !"0".equals(errCode)) {
			throw new RuntimeException(user.getErrMsg());
		}

		return user;
	}

	@Override
	public Result authSucc(String accessToken, String userId) throws RuntimeException {
		if (StringUtils.isBlank(accessToken)) {
			throw new RuntimeException("access_token cannot be null.");
		}

		if (StringUtils.isBlank(userId)) {
			throw new RuntimeException("userid cannot be null.");
		}

		Result result = null;

		try {
			result =
				JSON.parseObject(HttpUtil.get(IUserInfoService.HTTPS_AUTH_SUCC_URL.replace("$ACCESS_TOKEN$",
					accessToken.trim()).replace("$USERID$", userId.trim())), Result.class);
		} catch (Exception e) {
			logger.error(accessToken + "&" + userId, e);

			throw new RuntimeException("HttpUtil error.", e);
		}

		if (result == null) {
			throw new RuntimeException("result is null.");
		}

		String errCode = result.getErrCode();
		if (StringUtils.isNotBlank(errCode) && !"0".equals(errCode)) {
			throw new RuntimeException(result.getErrMsg());
		}

		return result;
	}

	@Override
	public Result createUser(User user) throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

}
