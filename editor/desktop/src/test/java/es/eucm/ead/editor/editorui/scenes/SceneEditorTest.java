/**
 * eAdventure is a research project of the
 *    e-UCM research group.
 *
 *    Copyright 2005-2014 e-UCM research group.
 *
 *    You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *
 *    e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *
 *          CL Profesor Jose Garcia Santesmases 9,
 *          28040 Madrid (Madrid), Spain.
 *
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *
 * ****************************************************************************
 *
 *  This file is part of eAdventure
 *
 *      eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with eAdventure.  If not, see <http://www.gnu.org/licenses/>.
 */
package es.eucm.ead.editor.editorui.scenes;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.scenes.scene2d.Group;

import es.eucm.ead.editor.editorui.EditorUITest;
import es.eucm.ead.editor.ui.scenes.DesktopSceneEditor;
import es.eucm.ead.editor.ui.scenes.ribbon.SceneRibbon;
import es.eucm.ead.editor.view.widgets.layouts.LinearLayout;
import es.eucm.ead.editor.view.widgets.scenes.SceneEditor;

public class SceneEditorTest extends EditorUITest {
	@Override
	protected void builUI(Group root) {
		LinearLayout container = new LinearLayout(false);
		SceneEditor sceneEditor = new DesktopSceneEditor(controller);

		container.add(new SceneRibbon(controller)).expandX();
		container.add(sceneEditor).expand(true, true).getActor().toBack();
		container.setFillParent(true);
		root.addActor(container);
	}

	public static void main(String[] args) {
		new LwjglApplication(new SceneEditorTest(), "Scene Editor test", 1000,
				600);
	}

}
