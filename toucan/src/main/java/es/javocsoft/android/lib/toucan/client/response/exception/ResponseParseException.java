/**
 * JavocSoft Toucan API Client Library.
 *
 *   Copyright (C) 2013 JavocSoft - Javier González Serrano.
 *
 *   This file is part of JavcoSoft Toucan API client Library.
 *
 *   This library is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   JavocSoft Toucan Client Library is distributed in the hope that it will 
 *   be useful, but WITHOUT ANY WARRANTY; without even the implied warranty 
 *   of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with JavocSoft Toucan API Client Library. If not, 
 *   see <http://www.gnu.org/licenses/>.
 */
package es.javocsoft.android.lib.toucan.client.response.exception;

/**
 * 
 * @author JavocSoft Team 
 * @version 1.0 $$
 * $Author: jgonzalez $
 * $Date: 2015-04-22 17:35:47 +0200 (mi., 22 abr. 2015) $
 *
 */
public class ResponseParseException extends Exception {

	private static final long serialVersionUID = 1L;

	
	public ResponseParseException() {
		
	}

	public ResponseParseException(String message) {
		super(message);		
	}

	public ResponseParseException(Throwable cause) {
		super(cause);		
	}

	public ResponseParseException(String message, Throwable cause) {
		super(message, cause);		
	}

}
