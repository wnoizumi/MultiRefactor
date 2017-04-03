/*
 * $Header: /cvsroot/jrdf/jrdf/src/org/jrdf/graph/Container.java,v 1.3 2004/08/15 20:42:08 newmana Exp $
 * $Revision: 1.3 $
 * $Date: 2004/08/15 20:42:08 $
 *
 * ====================================================================
 *
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2004 The JRDF Project.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        the JRDF Project (http://jrdf.sf.net/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The JRDF Project" and "JRDF" must not be used to endorse
 *    or promote products derived from this software without prior written
 *    permission. For written permission, please contact
 *    newmana@users.sourceforge.net.
 *
 * 5. Products derived from this software may not be called "JRDF"
 *    nor may "JRDF" appear in their names without prior written
 *    permission of the JRDF Project.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the JRDF Project.  For more
 * information on JRDF, please see <http://jrdf.sourceforge.net/>.
 */

package org.jrdf.graph;

// Java 2 standard packages
import java.util.Collection;

/**
 * A Container is a grouping of statements.  A container can either be a
 * {@link Alternative}, {@link Bag}, or {@link Sequence}.
 *
 * See <A HREF="http://www.w3.org/TR/rdf-primer/#containers">4.1 RDF Containers</A>.
 *
 * Alternatives are unordered lists that do not allow duplicates.  Bags are
 * unordered lists that allow duplicates.  Sequences are ordered lists that
 * allows duplicates.
 *
 * Currently, this is just an extension of java.util.Collection that throws
 * exceptions when inappropriate objects are used, for example non-ObjectNodes
 * are added.
 *
 * @author Andrew Newman
 *
 * @version $Revision: 1.3 $
 */
public interface Container extends Collection {

  /**
   * @throws IllegalArgumentException if the given object is not the correct
   *   type, ObjectNode.
   */
  public boolean add(Object o) throws IllegalArgumentException;

  /**
   * Always returns true.  It will only remove one element if there is more
   * than one is the container.  This can get quite costly as it must iterate
   * through the values from the start to end looking for the object.
   *
   * @throws IllegalArgumentException if the given object is not the correct
   *   type, ObjectNode.
   */
  public boolean remove(Object o) throws IllegalArgumentException;
}
