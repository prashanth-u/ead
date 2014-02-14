/**
 * eAdventure is a research project of the
 *    e-UCM research group.
 *
 *    Copyright 2005-2013 e-UCM research group.
 *
 *    You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *
 *    e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *
 *          C Profesor Jose Garcia Santesmases sn,
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
package es.eucm.ead.editor.view.widgets.mockup;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import es.eucm.ead.editor.control.Controller;
import es.eucm.ead.editor.view.widgets.mockup.panels.LateralOptionsPanel;
import es.eucm.ead.editor.view.widgets.mockup.panels.LateralPanel;

public class Options extends Table{

	private Button optButton;
	private LateralPanel optPanel;
	
	private Rectangle rtmp;
	
	private boolean opened;
	
	String IC_OPTIONS = "ic_settings";
	
	public Options(Controller controller, Skin skin) {
		super(skin);
		
		optButton = new ImageButton(skin, IC_OPTIONS);
		optButton.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				opened = !optPanel.isVisible();
				if(optPanel.isVisible()){
					optPanel.hide();
				} else {
					optPanel.show();
				}
				return false;
			}
		});
				
		optPanel = new LateralOptionsPanel(controller, skin);
		opened=optPanel.isVisible();
		rtmp = new Rectangle();
		rtmp.set(optPanel.getX(), optPanel.getY(), optPanel.getWidth(), optPanel.getHeight());
		
		this.add(optButton).top().right().expand();
		this.row();
		this.add(optPanel).center().right().expand();
	}
	
	public boolean isOpened(){
		return opened;
	}
	
	public Button getButton(){
		return optButton;
	}
	
	public LateralPanel getPanel(){
		return optPanel;
	}

}
