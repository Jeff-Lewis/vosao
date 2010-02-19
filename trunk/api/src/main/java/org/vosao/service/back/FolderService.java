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

package org.vosao.service.back;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.vosao.business.decorators.TreeItemDecorator;
import org.vosao.entity.FolderEntity;
import org.vosao.service.AbstractService;
import org.vosao.service.ServiceResponse;
import org.vosao.service.vo.FolderRequestVO;


public interface FolderService extends AbstractService {
	
	FileService getFileService();
	void setFileService(FileService bean);
	
	FolderPermissionService getFolderPermissionService();
	void setFolderPermissionService(FolderPermissionService bean);
	
	GroupService getGroupService();
	void setGroupService(GroupService bean);

	TreeItemDecorator<FolderEntity> getTree();
	
	TreeItemDecorator<FolderEntity> getFolderByPath(String path);

	FolderEntity createFolderByPath(String path) 
			throws UnsupportedEncodingException;

	String getFolderPath(final String folderId);
	
	FolderEntity getFolder(final String id);
	
	FolderRequestVO getFolderRequest(final String folderId,
			final String folderParentId);

	List<FolderEntity> getByParent(final String id);
	
	ServiceResponse saveFolder(final Map<String, String> vo);
	
	ServiceResponse deleteFolder(final List<String> ids);
}
