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

package org.vosao.entity;

import org.vosao.enums.StructureTemplateType;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class StructureTemplateEntity extends BaseMapstoEntityImpl {

	private static final long serialVersionUID = 3L;

	private String title;
	private String name;
	private Long structureId;
	private StructureTemplateType type;
	private String content;
	
	public StructureTemplateEntity() {
		type = StructureTemplateType.VELOCITY;
	}
	
	public StructureTemplateEntity(String name, String title, Long structureId, 
			StructureTemplateType type, String content) {
		this();
		this.name = name;
		this.title = title;
		this.structureId = structureId;
		this.type = type;
		this.content = content;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public Long getStructureId() {
		return structureId;
	}

	public void setStructureId(Long structureId) {
		this.structureId = structureId;
	}

	public StructureTemplateType getType() {
		return type;
	}

	public String getTypeString() {
		return type != null ? type.name() : "null";
	}
	
	public void setType(StructureTemplateType value) {
		this.type = value;
	}

	public boolean isVelocity() {
		return type.equals(StructureTemplateType.VELOCITY);
	}

	public boolean isXSLT() {
		return type.equals(StructureTemplateType.XSLT);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
