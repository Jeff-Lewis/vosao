/**
 * Vosao CMS. Simple CMS for Google App Engine.
 * 
 * Copyright (C) 2009-2010 Vosao development team.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * email: vosao.dev@gmail.com
 */

package org.vosao.i18n;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.vosao.business.Business;
import org.vosao.common.VosaoContext;
import org.vosao.entity.ConfigEntity;

/**
 * Message bundle helper class for creatng localized messages from Java code.
 * 
 * @author Alexander Oleynik
 *
 */
public class Messages {

	public static final String LOCALE_KEY = "locale";

	private static final Locale[] supportedLocales = {
		Locale.ENGLISH,
		Locale.GERMAN,
		new Locale("ru"),
		Locale.JAPANESE,
		Locale.TRADITIONAL_CHINESE,
		Locale.SIMPLIFIED_CHINESE};
	
	private static Map<Locale, VosaoResourceBundle> bundles = 
			new HashMap<Locale, VosaoResourceBundle>();
	
	private static VosaoResourceBundle getBundle(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Locale sessionLocale = (Locale)session.getAttribute(LOCALE_KEY);
		Locale locale = sessionLocale != null ? sessionLocale : 
			request.getLocale();
		return getBundle(locale);
	}
	
	private static VosaoResourceBundle getDefaultBundle() {
		return getBundle(Locale.ENGLISH);
	}

	private static VosaoResourceBundle getBundle(Locale locale) {
		if (!bundles.containsKey(locale)) {
			bundles.put(locale, new VosaoResourceBundle(locale));
		}
		return bundles.get(locale);
	}
	
	public static String get(String key, Object...objects) {
		VosaoContext ctx = VosaoContext.getInstance();
		String pattern = "not found";
		if (isLocaleSupported(ctx.getLocale())) {
			pattern = getBundle(ctx.getRequest()).getString(key);
		}
		else {
			pattern = getDefaultBundle().getString(key);
		}
		if (objects != null) {
			MessageFormat formatter = new MessageFormat("");
			formatter.setLocale(ctx.getLocale());
			formatter.applyPattern(pattern);
			pattern = formatter.format(objects);
		}
		return pattern;
	}
	
	private static Business getBusiness() {
		return VosaoContext.getInstance().getBusiness();
	}
	
	/**
	 * Generate JavaScript JSON message bundle for JavaScript messages 
	 * localization.
	 * @return JS file.
	 */
	public static String getJSMessages() {
		VosaoContext ctx = VosaoContext.getInstance();
		Map<String, String> messages = new HashMap<String, String>();
		VosaoResourceBundle defaultBundle = getDefaultBundle();
		for (String key : Collections.list(defaultBundle.getKeys())) {
			try {
				messages.put(key, defaultBundle.getString(key));
			}
			catch (MissingResourceException e) {
			}
		}
		VosaoResourceBundle bundle = getBundle(ctx.getRequest());
		for (String key : Collections.list(bundle.getKeys())) {
			try {
				messages.put(key, bundle.getString(key));
			}
			catch (MissingResourceException e) {
			}
		}
		ConfigEntity config = VosaoContext.getInstance().getConfig();
		StringBuffer result = new StringBuffer();
		result.append("var locale = '").append(ctx.getLocale().toString())
				.append("';\n");
		result.append("var locale_language = '")
			.append(ctx.getLocale().getLanguage()).append("';\n");
		result.append("var default_language = '")
			.append(getBusiness().getDefaultLanguage()).append("';\n");
		result.append(
				"function messages(key) {\n" 
			  + "  if (_messages[key] == 'undefined') {\n"
			  + "    return '__' + key + '__';\n"
			  + "  } else {\n"
			  + "    return _messages[key];\n"
			  + "  }\n"
			  + "}\n");
		result.append("var _messages = {\n");
		int i = 0;
		for (String key : messages.keySet()) {
			if (i++ > 0) {
				result.append(",");
			}
			result.append(" '").append(key).append("' : '")
				.append(filterForJS(messages.get(key)))
				.append("'\n");
		}
		result.append("};");
		return result.toString();
	}
	
	private static String filterForJS(String msg) {
		return msg.replaceAll("\n", "").replaceAll("'", "\\\\'");
	}
	
	public static boolean isLocaleSupported(Locale locale) {
		for (Locale l : supportedLocales) {
			if (l.equals(locale)) {
				return true;
			}
		}
		return false;
	}
	
}
