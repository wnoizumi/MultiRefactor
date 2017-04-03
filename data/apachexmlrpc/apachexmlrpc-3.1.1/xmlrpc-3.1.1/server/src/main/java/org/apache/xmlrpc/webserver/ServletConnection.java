/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 */
package org.apache.xmlrpc.webserver;

import java.io.IOException;
import java.net.Socket;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.xmlrpc.util.ThreadPool.InterruptableTask;


/** {@link org.apache.xmlrpc.webserver.ServletWebServer ServletWebServer's}
 * {@link org.apache.xmlrpc.util.ThreadPool.Task} for handling a single
 * servlet connection.
 */
public class ServletConnection implements InterruptableTask {
	private final HttpServlet servlet;
	private final Socket socket;
	private final HttpServletRequest request;
	private final HttpServletResponse response;
    private boolean shuttingDown;

	/** Creates a new instance.
	 * @param pServlet The servlet, which ought to handle the request.
	 * @param pSocket The socket, to which the client is connected.
	 * @throws IOException
	 */
	public ServletConnection(HttpServlet pServlet, Socket pSocket) throws IOException {
		servlet = pServlet;
		socket = pSocket;
		request = new HttpServletRequestImpl(socket);
		response = new HttpServletResponseImpl(socket);
	}

	public void run() throws Throwable {
        try {
            servlet.service(request, response);
        } catch (Throwable t) {
            if (!shuttingDown) {
                throw t;
            }
        }
	}

    public void shutdown() throws Throwable {
        shuttingDown = true;
        socket.close();
    }
}
