/*
 * Copyright 2000-2014 Vaadin Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.tests.server.component.abstractcomponent;

import junit.framework.TestCase;

import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.declarative.DesignContext;

/**
 * Test case for reading the attributes of the abstract component from design
 * 
 * @author Vaadin Ltd
 */
public class TestSynchronizeFromDesign extends TestCase {

    private DesignContext ctx;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ctx = new DesignContext();
    }

    public void testSynchronizeId() {
        Element design = createDesign("id", "testId");
        AbstractComponent component = getComponent();
        component.synchronizeFromDesign(design, ctx);
        assertEquals("testId", component.getId());
    }

    public void testSynchronizePrimaryStyleName() {
        Element design = createDesign("primary-style-name", "test-style");
        AbstractComponent component = getComponent();
        component.synchronizeFromDesign(design, ctx);
        assertEquals("test-style", component.getPrimaryStyleName());
    }

    public void testSynchronizeCaption() {
        Element design = createDesign("caption", "test-caption");
        AbstractComponent component = getComponent();
        component.synchronizeFromDesign(design, ctx);
        assertEquals("test-caption", component.getCaption());
    }

    public void testSynchronizeLocale() {
        Element design = createDesign("locale", "fi_FI");
        AbstractComponent component = getComponent();
        component.synchronizeFromDesign(design, ctx);
        assertEquals("fi", component.getLocale().getLanguage());
        assertEquals("FI", component.getLocale().getCountry());
    }

    public void testSynchronizeExternalIcon() {
        Element design = createDesign("icon", "http://example.com/example.gif");
        AbstractComponent component = getComponent();
        component.synchronizeFromDesign(design, ctx);
        assertTrue("Incorrect resource type returned", component.getIcon()
                .getClass().isAssignableFrom(ExternalResource.class));
    }

    public void testSynchronizeThemeIcon() {
        Element design = createDesign("icon", "theme://example.gif");
        AbstractComponent component = getComponent();
        component.synchronizeFromDesign(design, ctx);
        assertTrue("Incorrect resource type returned", component.getIcon()
                .getClass().isAssignableFrom(ThemeResource.class));
    }

    public void testSynchronizeFileResource() {
        Element design = createDesign("icon", "img/example.gif");
        AbstractComponent component = getComponent();
        component.synchronizeFromDesign(design, ctx);
        assertTrue("Incorrect resource type returned", component.getIcon()
                .getClass().isAssignableFrom(FileResource.class));
    }

    public void testSynchronizeImmediate() {
        Element design = createDesign("immediate", "true");
        AbstractComponent component = getComponent();
        component.synchronizeFromDesign(design, ctx);
        assertEquals(true, component.isImmediate());
    }

    public void testSynchronizeDescription() {
        Element design = createDesign("description", "test-description");
        AbstractComponent component = getComponent();
        component.synchronizeFromDesign(design, ctx);
        assertEquals("test-description", component.getDescription());
    }

    public void testSynchronizeComponentError() {
        Element design = createDesign("error", "<div>test-error</div>");
        AbstractComponent component = getComponent();
        component.synchronizeFromDesign(design, ctx);
        assertEquals("<div>test-error</div>", component.getComponentError()
                .getFormattedHtmlMessage());
    }

    public void testSynchronizeSizeFull() {
        Element design = createDesign("size-full", "");
        AbstractComponent component = getComponent();
        component.synchronizeFromDesign(design, ctx);
        assertEquals(100, component.getWidth(), 0.1f);
        assertEquals(100, component.getHeight(), 0.1f);
    }

    public void testSynchronizeSizeAuto() {
        Element design = createDesign("size-auto", "");
        AbstractComponent component = getComponent();
        component.synchronizeFromDesign(design, ctx);
        assertEquals(-1, component.getWidth(), 0.1f);
        assertEquals(-1, component.getHeight(), 0.1f);
    }

    public void testSynchronizeHeightFull() {
        Element design = createDesign("height-full", "");
        AbstractComponent component = getComponent();
        component.synchronizeFromDesign(design, ctx);
        assertEquals(100, component.getHeight(), 0.1f);
    }

    public void testSynchronizeHeightAuto() {
        Element design = createDesign("height-auto", "");
        AbstractComponent component = getComponent();
        component.synchronizeFromDesign(design, ctx);
        assertEquals(-1, component.getHeight(), 0.1f);
    }

    public void testSynchronizeWidthFull() {
        Element design = createDesign("width-full", "");
        AbstractComponent component = getComponent();
        component.synchronizeFromDesign(design, ctx);
        assertEquals(100, component.getWidth(), 0.1f);
    }

    public void testSynchronizeWidthAuto() {
        Element design = createDesign("width-auto", "");
        AbstractComponent component = getComponent();
        component.synchronizeFromDesign(design, ctx);
        assertEquals(-1, component.getWidth(), 0.1f);
    }

    public void testSynchronizeWidth() {
        Element design = createDesign("width", "12px");
        AbstractComponent component = getComponent();
        component.synchronizeFromDesign(design, ctx);
        assertEquals(12, component.getWidth(), 0.1f);
        assertEquals(com.vaadin.server.Sizeable.Unit.PIXELS,
                component.getWidthUnits());
    }

    public void testSynchronizeHeight() {
        Element design = createDesign("height", "12px");
        AbstractComponent component = getComponent();
        component.synchronizeFromDesign(design, ctx);
        assertEquals(12, component.getHeight(), 0.1f);
        assertEquals(com.vaadin.server.Sizeable.Unit.PIXELS,
                component.getHeightUnits());

    }

    private AbstractComponent getComponent() {
        return new Label();
    }

    private Element createDesign(String key, String value) {
        Attributes attributes = new Attributes();
        attributes.put(key, value);
        Element node = new Element(Tag.valueOf("v-label"), "", attributes);
        return node;
    }
}