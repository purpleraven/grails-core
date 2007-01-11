/*
 * Copyright 2004-2005 the original author or authors.
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
package org.codehaus.groovy.grails.web.servlet;

import org.codehaus.groovy.grails.web.servlet.mvc.AbstractGrailsControllerTests

/**
 * Tests for the render method
 *
 * @author Graeme Rocher
 *
 */
class RenderMethodTests extends AbstractGrailsControllerTests {

	void testRenderText() {
		runTest {
			def mockController = ga.getController("RenderController").newInstance()
			mockController.renderText.call()
			
			def request = mockController.request
			assert request != null
			def response = mockController.response
			
			assert response != null
			
			assertEquals "test render", response.delegate.contentAsString
			
		}		
	}
	
	void testRenderXml() {
		runTest {
			def mockController = ga.getController("RenderController").newInstance()
		
			mockController.renderXML.call()
			
			def request = mockController.request
			assert request != null
			def response = mockController.response
			
			assert response != null
			
			assertEquals "<hello>world</hello>", response.delegate.contentAsString
			assertEquals "text/xml", response.contentType
		}		
	}
	
	void testRenderJSON() {
		// TODO	
	}
	
	void testRenderTemplate() {
		
		runTest {
			def mockController = ga.getController("RenderController").newInstance()
			
			request.setAttribute( GrailsApplicationAttributes.CONTROLLER, mockController)
			webRequest.controllerName = "render"
			mockController.renderTemplate.call()
			
			def response = mockController.response
			
			assertEquals "hello world!", response.delegate.contentAsString
		}
	}

	void onSetUp() {
		gcl.parseClass(
'''
class RenderController {
	def renderText = {
		render "test render"
	}
	def renderXML = {
		render(contentType:"text/xml") {
			hello("world")
		}
	}
	def renderTemplate = {
		render(template:"testTemplate", model:[hello:"world"])
	}
}
'''				
		)
	}
}
