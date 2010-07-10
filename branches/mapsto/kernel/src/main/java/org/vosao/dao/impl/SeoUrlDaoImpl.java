/**
 * Vosao CMS. Simple CMS for Google App Engine.
 * Copyright (C) 2009 Vosao development team
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * email: vosao.dev@gmail.com
 */

package org.vosao.dao.impl;

import org.mapsto.Filter;
import org.mapsto.Query;
import org.vosao.dao.BaseMapstoDaoImpl;
import org.vosao.dao.SeoUrlDao;
import org.vosao.entity.SeoUrlEntity;

public class SeoUrlDaoImpl extends BaseMapstoDaoImpl<SeoUrlEntity> 
		implements SeoUrlDao {

	public SeoUrlDaoImpl() {
		super("SeoUrlEntity");
	}

	public SeoUrlEntity getByFrom(final String from) {
		Query<SeoUrlEntity> q = newQuery();
		q.addFilter("fromLink", Filter.EQUAL, from);
		return q.selectOne("getByFrom", params(from));
	}

}
