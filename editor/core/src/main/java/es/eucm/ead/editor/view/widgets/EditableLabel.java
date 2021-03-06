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
package es.eucm.ead.editor.view.widgets;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;

import es.eucm.ead.editor.control.Controller;
import es.eucm.ead.editor.control.actions.model.ChangeLabelText;
import es.eucm.ead.editor.model.Model.FieldListener;
import es.eucm.ead.editor.model.events.FieldEvent;
import es.eucm.ead.schema.components.controls.Label;
import es.eucm.ead.schemax.FieldName;

/**
 * A label that can be edited. When it is double clicked, it converts in a text
 * field, and it can be edited. Pressing ENTER or ESC finish the edition the
 * text field is converted back to a label.
 */
public class EditableLabel extends TextField implements FieldListener {

	private int tapCount;

	private Label componentLabel;

	/**
	 * @param tapCount
	 *            number of taps that makes this label editable
	 */
	public EditableLabel(String text, Skin skin, int tapCount) {
		this(text, skin.get(TextFieldStyle.class), tapCount);
	}

	/**
	 * @param tapCount
	 *            number of taps that makes this label editable
	 */
	public EditableLabel(String text, Skin skin, String styleName, int tapCount) {
		this(text, skin.get(styleName, TextFieldStyle.class), tapCount);
	}

	/**
	 * @param tapCount
	 *            number of taps that makes this label editable
	 */
	public EditableLabel(String text, TextFieldStyle style, int tapCount) {
		super(text, style);
		this.tapCount = tapCount;
		init();
	}

	private void init() {
		setDisabled(true);
		addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (getTapCount() == tapCount) {
					setDisabled(false);
					getStage().setKeyboardFocus(EditableLabel.this);
					selectAll();
				}
			}

			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				switch (keycode) {
				case Keys.ESCAPE:
				case Keys.ENTER:
					setDisabled(true);
					getStage().setKeyboardFocus(getParent());
					return true;
				}
				return false;
			}
		});
		addListener(new FocusListener() {
			@Override
			public void keyboardFocusChanged(FocusEvent event, Actor actor,
					boolean focused) {
				setDisabled(!focused);
			}
		});
	}

	public void initLabelListener(final Controller controller, Label component) {
		this.componentLabel = component;
		controller.getModel().addFieldListener(componentLabel, this);
		addListener(new InputListener() {
			public boolean keyTyped(InputEvent event, char keycode) {
				if (!componentLabel.getText().equals(getText())) {
					controller.action(ChangeLabelText.class, componentLabel,
							getText());
					return true;
				}
				return false;
			}
		});
	}

	@Override
	public void modelChanged(FieldEvent event) {
		if (!componentLabel.getText().equals(getText())) {
			setText(componentLabel.getText());
			setCursorPosition(getText().length());
		}

	}

	@Override
	public boolean listenToField(String fieldName) {
		return (fieldName.equals(FieldName.TEXT));
	}

}
