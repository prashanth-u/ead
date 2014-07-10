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
package es.eucm.ead.editor.editorui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import es.eucm.ead.editor.control.actions.editor.ShowDialog;
import es.eucm.ead.editor.view.builders.classic.dialogs.InfoDialogBuilder;
import es.eucm.ead.editor.view.controllers.DialogController.DialogButtonListener;
import es.eucm.ead.editor.view.listeners.ActionOnClickListener;
import es.eucm.ead.editor.view.widgets.layouts.LinearLayout;

public class DialogsTest extends EditorUITest {
	@Override
	protected void builUI(Group root) {
		Skin skin = controller.getApplicationAssets().getSkin();

		LinearLayout container = new LinearLayout(true,
				skin.getDrawable("blank"));
		container.setFillParent(true);

		TextButton button = new TextButton("Info dialog", skin);
		button.addListener(new ActionOnClickListener(controller,
				ShowDialog.class, InfoDialogBuilder.class, "Info message",
				"This is some important information", "OK",
				new DialogButtonListener() {
					@Override
					public void selected() {
						Gdx.app.log("DialogsTest", "OK pressed");
					}
				}, "Close", null));
		container.add(button);

		root.addActor(container);
	}

	public static void main(String[] args) {
		new LwjglApplication(new DialogsTest(), "Dialogs Test", 800, 600);
	}
}
