/*!
* This program is free software; you can redistribute it and/or modify it under the
* terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
* Foundation.
*
* You should have received a copy of the GNU Lesser General Public License along with this
* program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
* or from the Free Software Foundation, Inc.,
* 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*
* This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
* without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
* See the GNU Lesser General Public License for more details.
*
* Copyright (c) 2002-2014 Pentaho Corporation..  All rights reserved.
*/
package org.pentaho.di.monitor.trans;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.extension.ExtensionPoint;
import org.pentaho.di.core.extension.ExtensionPointInterface;
import org.pentaho.di.monitor.IKettleMonitoringEvent;
import org.pentaho.di.monitor.MonitorAbstract;
import org.pentaho.di.trans.Trans;

/**
 * @see http://wiki.pentaho.com/display/EAI/PDI+Extension+Point+Plugins
 */

@ExtensionPoint(
  extensionPointId = "TransformationFinish",
  id = "TransformationFinishMonitor",
  description = "A transformation finishes"
)
public class TransformationFinishMonitor extends MonitorAbstract implements ExtensionPointInterface {

  @Override
  public IKettleMonitoringEvent toKettleEvent( Object o ) throws KettleException {

    if ( o == null || !( o instanceof Trans ) ) {
      return null;
    }

    TransformationEvent transEvent = new TransformationEvent( TransformationEvent.EventType.FINISHED ).build( (Trans) o );

    getLog().logDebug( "TransformationFinishMonitor - " + ToStringBuilder.reflectionToString( o )
      + " , completed in " + ( transEvent.getStartTimeMillis() > 0 ?
      ( ( ( transEvent.getEndTimeMillis() - transEvent.getStartTimeMillis() ) / 1000 ) % 60 ) : 0 ) + " seconds");

    return transEvent;
  }
}