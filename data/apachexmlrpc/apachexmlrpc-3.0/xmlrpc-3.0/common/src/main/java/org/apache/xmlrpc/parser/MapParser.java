/*
 * Copyright 1999,2005 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.xmlrpc.parser;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.ws.commons.util.NamespaceContextImpl;
import org.apache.xmlrpc.common.TypeFactory;
import org.apache.xmlrpc.common.XmlRpcStreamConfig;
import org.apache.xmlrpc.serializer.MapSerializer;
import org.apache.xmlrpc.serializer.TypeSerializerImpl;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


/** {@link org.apache.xmlrpc.parser.TypeParser} implementation
 * for maps.
 */
public class MapParser extends RecursiveTypeParserImpl {
	private int level = 0;
	private String name;
	private Map map;
	private boolean inName, inValue, doneValue;

	/** Creates a new instance.
	 * @param pConfig The request or response configuration.
	 * @param pContext The namespace context.
	 * @param pFactory The factory.
	 */
	public MapParser(XmlRpcStreamConfig pConfig,
					 NamespaceContextImpl pContext,
					 TypeFactory pFactory) {
		super(pConfig, pContext, pFactory);
	}

	protected void addResult(Object pResult) throws SAXException {
		if (name == null) {
			throw new SAXParseException("Invalid state: Expected name",
										getDocumentLocator());
		} else {
			if (map.containsKey(name)) {
				throw new SAXParseException("Duplicate name: " + name,
											getDocumentLocator());
			} else {
				map.put(name, pResult);
			}
		}
	}

	public void startDocument() throws SAXException {
		super.startDocument();
		level = 0;
		map = new HashMap();
		inValue = inName = false;
	}

	public void characters(char[] pChars, int pOffset, int pLength) throws SAXException {
		if (inName) {
			String s = new String(pChars, pOffset, pLength);
			name = name == null ? s : name + s;
		} else {
			super.characters(pChars, pOffset, pLength);
		}
	}

	public void ignorableWhitespace(char[] pChars, int pOffset, int pLength) throws SAXException {
		if (inName) {
			characters(pChars, pOffset, pLength);
		} else {
			super.ignorableWhitespace(pChars, pOffset, pLength);
		}
	}

	public void startElement(String pURI, String pLocalName, String pQName,
							 Attributes pAttrs) throws SAXException {
		switch (level++) {
			case 0:
				if (!"".equals(pURI)  ||  !MapSerializer.STRUCT_TAG.equals(pLocalName)) {
					throw new SAXParseException("Expected " + MapSerializer.STRUCT_TAG + ", got "
												+ new QName(pURI, pLocalName),
												getDocumentLocator());
				}
				break;
			case 1:
				if (!"".equals(pURI)  ||  !MapSerializer.MEMBER_TAG.equals(pLocalName)) {
					throw new SAXParseException("Expected " + MapSerializer.MEMBER_TAG + ", got "
												+ new QName(pURI, pLocalName),
												getDocumentLocator());
				}
				doneValue = inName = inValue = false;
				name = null;
				break;
			case 2:
				if (doneValue) {
					throw new SAXParseException("Expected /" + MapSerializer.MEMBER_TAG
												+ ", got " + new QName(pURI, pLocalName),
												getDocumentLocator());
				}
				if ("".equals(pURI)  &&  MapSerializer.NAME_TAG.equals(pLocalName)) {
					if (name == null) {
						inName = true;
					} else {
						throw new SAXParseException("Expected " + TypeSerializerImpl.VALUE_TAG
													+ ", got " + new QName(pURI, pLocalName),
													getDocumentLocator());
					}
				} else if ("".equals(pURI)  &&  TypeSerializerImpl.VALUE_TAG.equals(pLocalName)) {
					if (name == null) {
						throw new SAXParseException("Expected " + MapSerializer.NAME_TAG
													+ ", got " + new QName(pURI, pLocalName),
													getDocumentLocator());
					} else {
						inValue = true;
						startValueTag();
					}
					
				}
				break;
			default:
				super.startElement(pURI, pLocalName, pQName, pAttrs);
				break;
		}
	}

	public void endElement(String pURI, String pLocalName, String pQName) throws SAXException {
		switch (--level) {
			case 0:
				setResult(map);
				break;
			case 1:
				break;
			case 2:
				if (inName) {
					inName = false;
				} else if (inValue) {
					endValueTag();
					doneValue = true;
				}
				break;
			default:
				super.endElement(pURI, pLocalName, pQName);
		}
	}
}
