/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mypower24.smd.rar.outbound;

import com.mypower24.smd.rar.lib.JcMessage;
import java.util.logging.Logger;
import javax.resource.ResourceException;
import com.mypower24.smd.rar.api.out.JcConnection;

/**
 *
 * @author henry
 */
public class JcConnectionImpl implements JcConnection {

    private static final Logger log = Logger.getLogger("JcConnectionImpl");
    private JcManagedConnection mconnection;
    private boolean valid;

    JcConnectionImpl(JcManagedConnection mconnection) {
        this.mconnection = mconnection;
        valid = true;
    }

    public JcManagedConnection getManagedConnection() {
        return mconnection;
    }

    public void setManagedConnection(JcManagedConnection mconnection) {
        this.mconnection = mconnection;
    }

    /* Called by the managed connection to invalidate this handle */
    public void invalidate() {
        valid = false;
    }

    @Override
    public JcMessage send(JcMessage req) throws ResourceException {
//        log.info("[JcConnectionImpl] send()");

        if (valid) {

//            log.info("[JcConnectionImpl] send()");
            JcMessage resp = mconnection.sendCommandToServer(req);
            mconnection.closeHandle(this);
            return resp;
        }

        throw new ResourceException("Connection handle is invalid");
    }

    @Override
    public void close() throws ResourceException {
//        log.info("[JcConnectionImpl] close()");
        mconnection = null;
    }
}
