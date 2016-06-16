package com.vaadin.tests.databinding;

import com.vaadin.event.typed.Registration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.communication.data.typed.Binder;
import com.vaadin.tests.components.AbstractTestUIWithLog;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.components.fields.CheckBox;

public class CheckBoxTestUI extends AbstractTestUIWithLog {
    
    private class SomeBean {
        
        private boolean val;

        public boolean getVal() {
            return val;
        }

        public void setVal(boolean val) {
            this.val = val;
        }
        
    }

    @Override
    protected void setup(VaadinRequest request) {
        CheckBox cb = new CheckBox("Click me", true);
        cb.setImmediate(true);
        
        SomeBean bean = new SomeBean();
        Binder<SomeBean> binder = new Binder<>();
        binder.addField(cb, SomeBean::getVal, SomeBean::setVal);
        binder.bind(bean);
        
        SomeBean trueBean = new SomeBean();
        trueBean.setVal(true);
        
        Registration reg = cb.onChange(event -> {
            binder.save();
            Notification.show(
                  "checkbox val: " + cb.getValue() 
                + "\n bean val: " + bean.getVal()
                + "\n other bean val: " + trueBean.getVal());
        });
        Button rmReg = new Button("Registration.removeHandler()");
        Button swapBean = new Button("Bind other bean");
        rmReg.addClickListener(click -> reg.removeHandler());
        swapBean.addClickListener(click -> binder.bind(trueBean));
        addComponents(cb, rmReg, swapBean);
    }

}