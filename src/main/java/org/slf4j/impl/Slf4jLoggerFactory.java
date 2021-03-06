/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2009, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.slf4j.impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.jboss.logmanager.LogContext;

public final class Slf4jLoggerFactory implements ILoggerFactory {

    private static final org.jboss.logmanager.Logger.AttachmentKey<Logger> key = new org.jboss.logmanager.Logger.AttachmentKey<Logger>();

    public Logger getLogger(final String name) {
        Logger logger = LogContext.getLogContext().getAttachment(name, key);
        if (logger != null) {
            return logger;
        }
        final org.jboss.logmanager.Logger lmLogger = LogContext.getLogContext().getLogger(name);
        logger = lmLogger.getAttachment(key);
        if (logger != null) {
            return logger;
        }
        final Slf4jLogger newLogger = new Slf4jLogger(lmLogger);
        final Logger appearingLogger = lmLogger.attachIfAbsent(key, newLogger);
        return appearingLogger != null ? appearingLogger : newLogger;
    }
}
