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
package es.eucm.ead.engine;

import ashley.core.Entity;
import ashley.core.Family;
import es.eucm.ead.engine.assets.GameAssets;
import es.eucm.ead.engine.components.I18nTextComponent;
import es.eucm.ead.engine.processors.physics.VelocityProcessor;
import es.eucm.ead.engine.processors.behaviors.TouchProcessor;
import es.eucm.ead.engine.processors.controls.ButtonProcessor;
import es.eucm.ead.engine.processors.controls.TextButtonProcessor;
import es.eucm.ead.engine.processors.renderers.FramesProcessor;
import es.eucm.ead.engine.processors.renderers.ImageProcessor;
import es.eucm.ead.engine.processors.renderers.StatesProcessor;
import es.eucm.ead.engine.systems.*;
import es.eucm.ead.engine.systems.variables.VariablesSystem;
import es.eucm.ead.engine.systems.variables.VariablesSystem.VariableListener;
import es.eucm.ead.engine.systems.behaviors.TimersSystem;
import es.eucm.ead.engine.systems.behaviors.TouchSystem;
import es.eucm.ead.engine.systems.variables.VarsContext;
import es.eucm.ead.schema.components.behaviors.Touch;
import es.eucm.ead.schema.components.controls.Button;
import es.eucm.ead.schema.components.controls.TextButton;
import es.eucm.ead.schema.components.physics.Velocity;
import es.eucm.ead.schema.renderers.Frames;
import es.eucm.ead.schema.renderers.Image;
import es.eucm.ead.schema.renderers.States;

/**
 * Created by angel on 10/04/14.
 */
public class DefaultEngineInitializer implements EngineInitializer {

	@Override
	public void init(GameLayers gameLayers, GameAssets assets,
			GameLoop gameLoop, GameLoader gameLoader) {
		registerComponents(gameLoader.getEntitiesLoader(), assets, gameLoop);
		registerSystems(assets, gameLoop, gameLoader);
	}

	private void registerSystems(final GameAssets gameAssets,
			final GameLoop gameLoop, final GameLoader gameLoader) {

		VariablesSystem variablesSystem = new VariablesSystem();
		gameLoop.addSystem(variablesSystem);
		gameLoop.addSystem(new TouchSystem(gameLoop));
		gameLoop.addSystem(new TimersSystem(gameLoop));
		gameLoop.addSystem(new VelocitySystem());
		gameLoop.addSystem(new EffectsSystem(gameLoop, gameLoader));

		// Variables listeners
		variablesSystem.addListener(new LanguageVariableListener(gameLoop,
				gameAssets));
	}

	private void registerComponents(EntitiesLoader entitiesLoader,
			GameAssets gameAssets, GameLoop gameLoop) {
		// Components
		entitiesLoader.registerComponentProcessor(Image.class,
				new ImageProcessor(gameLoop, gameAssets));
		entitiesLoader.registerComponentProcessor(Frames.class,
				new FramesProcessor(gameLoop, gameAssets, entitiesLoader));
		entitiesLoader.registerComponentProcessor(Velocity.class,
				new VelocityProcessor(gameLoop));
		entitiesLoader.registerComponentProcessor(Button.class,
				new ButtonProcessor(gameLoop, gameAssets));
		entitiesLoader.registerComponentProcessor(TextButton.class,
				new TextButtonProcessor(gameLoop, gameAssets));
		entitiesLoader.registerComponentProcessor(Touch.class,
				new TouchProcessor(gameLoop));
		entitiesLoader.registerComponentProcessor(States.class,
				new StatesProcessor(gameLoop, gameAssets, entitiesLoader));
	}

	private static class LanguageVariableListener implements VariableListener {

		private GameLoop gameLoop;

		private GameAssets gameAssets;

		private LanguageVariableListener(GameLoop gameLoop,
				GameAssets gameAssets) {
			this.gameLoop = gameLoop;
			this.gameAssets = gameAssets;
		}

		@Override
		public boolean listensTo(String variableName) {
			return VarsContext.LANGUAGE_VAR.equals(variableName);
		}

		@Override
		public void variableChanged(String variable, Object value) {
			gameAssets.getI18N().setLang(value + "");
			for (Entity entity : gameLoop.getEntitiesFor(
					Family.getFamilyFor(I18nTextComponent.class)).values()) {
				I18nTextComponent text = entity
						.getComponent(I18nTextComponent.class);
				text.getText().setText(
						gameAssets.getI18N().m(text.getI18nKey()));
			}
		}
	}
}