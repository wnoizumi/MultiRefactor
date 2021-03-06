/*
 * $Header: /cvsroot/jrdf/jrdf/src/org/jrdf/graph/mem/TripleImpl.java,v 1.3 2004/09/09 02:02:24 pgearon Exp $
 * $Revision: 1.3 $
 * $Date: 2004/09/09 02:02:24 $
 *
 * ====================================================================
 *
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2003, 2004 The JRDF Project.  All rights reserved.
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

package org.jrdf.graph.mem;

import org.jrdf.graph.*;

/**
 * A trivial implementation of an RDF {@link Triple}.
 *
 * @author <a href="mailto:pgearon@users.sourceforge.net">Paul Gearon</a>
 *
 * @version $Revision: 1.3 $
 */
public class TripleImpl extends AbstractTriple {

  /**
   * Constructor for this Triple, only to be used by the NodeFactory.
   *
   * @param subject The subject node of this triple.
   * @param predicate The predicate node of this triple.
   * @param object The object node of this triple.
   */
  TripleImpl(SubjectNode subject, PredicateNode predicate, ObjectNode object) {
    this.subjectNode = subject;
    this.predicateNode = predicate;
    this.objectNode = object;
  }


  /**
   * Constructor for this Triple, only to be used by the iterators.
   *
   * @param subject The subject node id of this triple.
   * @param predicate The predicate node id of this triple.
   * @param object The object node id of this triple.
   */
  TripleImpl(GraphElementFactoryImpl nodeFactory, Long subject, Long predicate,
      Long object) {
    this.subjectNode = (SubjectNode) nodeFactory.getNodeById(subject);
    this.predicateNode = (PredicateNode) nodeFactory.getNodeById(predicate);
    this.objectNode = (ObjectNode) nodeFactory.getNodeById(object);
  }


  /**
   * Constructor for this Triple, only to be used by the variable iterators.
   *
   * @param first The first node id of this triple.
   * @param second The second node id of this triple.
   * @param third The third node id of this triple.
   */
  TripleImpl(GraphElementFactoryImpl nodeFactory, int var, Long first,
      Long second, Long third) {
    Long[] nodes = new Long[] { first, second, third };
    this.subjectNode = (SubjectNode) nodeFactory.getNodeById(nodes[var]);
    this.predicateNode = (PredicateNode) nodeFactory.getNodeById(
      nodes[(var + 1) % 3]);
    this.objectNode = (ObjectNode) nodeFactory.getNodeById(
      nodes[(var + 2) % 3]);
  }
}
