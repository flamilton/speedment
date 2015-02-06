/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.codegen;

import com.speedment.orm.config.model.ConfigEntity;
import com.speedment.orm.config.model.Dbms;
import com.speedment.orm.config.model.Project;
import com.speedment.orm.config.model.ProjectManager;
import com.speedment.orm.config.model.Schema;
import com.speedment.orm.config.model.Table;
import com.speedment.orm.platform.SpeedmentPlatform;
import com.speedment.util.Trees;
import java.util.stream.Collectors;

/**
 *
 * @author pemi
 */
public class TestConfigModel {

    public static void main(String[] args) {

        final SpeedmentPlatform sp = SpeedmentPlatform.getInstance().start();

        ProjectManager pm = sp.get(ProjectManager.class);

        final Table table = Table.newInstance();

        pm.add(Project.newInstance()
                .setName("myProject")
                .add(Dbms.newInstance()
                        .setName("someDatabase")
                        .add(Schema.newInstance()
                                .add(table)
                        )
                )
        );

        final String s = pm.childrenStream().map(Project::getName).collect(Collectors.joining());
        System.out.println(s);

        System.out.println(Trees.traverse((ConfigEntity) pm, ConfigEntity::childrenStream, Trees.TraversalOrder.DEPTH_FIRST_PRE)
                .map(ConfigEntity::getName)
                .collect(Collectors.joining(", ", "[", "]")
                ));

        final Schema schema = table.getParent().get();
        final Dbms dbms = schema.getParent().get();
        final Project project = dbms.getParent().get();

        System.out.println(table.getRelativeName(pm));
        System.out.println(table.getRelativeName(table));

        System.out.println(pm.getName());
        System.out.println(pm);

        System.out.println(pm.childrenStream().count());

    }

}