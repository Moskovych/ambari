/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ambari.view.hello;

import org.apache.ambari.view.ViewContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

//import java.io.BufferedReader;
//import java.io.InputStreamReader;

/**
 * Simple servlet for hello view.
 */
public class HelloServlet extends HttpServlet {

  /**
   * The view context.
   */
  private ViewContext viewContext;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    ServletContext context = config.getServletContext();
    viewContext = (ViewContext) context.getAttribute(ViewContext.CONTEXT_ATTRIBUTE);
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html");
    response.setStatus(HttpServletResponse.SC_OK);

    PrintWriter writer = response.getWriter();

    Map<String, String> properties = viewContext.getProperties();

    String name = properties.get("name");
    if (name == null) {
      name = viewContext.getUsername();
      if (name == null || name.length() == 0) {
        name = "world";
      }
    }
    writer.println("<h1>Hello " + name + "!</h1>");
    
    writer.println("<h2>Hue at server: <a href=\"http://hue_host:8888/\" target=\"_blank\">http://hue_host:8888/</a></h2>");
    writer.println("<h2>JupyterHub at server:<a href=\"http://jupyter_host:8000/\" target=\"_blank\">http://jupyter_host:8000/</a></h2>");
    writer.println("<h2>Your HDFS homedir: hdfs://hdfs_namenode:8020/user/" + name + "/ </h2>");
  }
}
