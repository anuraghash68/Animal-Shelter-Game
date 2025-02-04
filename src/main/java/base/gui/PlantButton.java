package base.gui;

import base.Game;
import base.graphicsservice.Rectangle;
import base.graphicsservice.RenderHandler;
import base.graphicsservice.Sprite;

import static base.constants.ColorConstant.GREEN;
import static base.constants.ColorConstant.YELLOW;

public class PlantButton extends GUIButton {

    private final String plantType;
    private final Game game;
    private boolean isGreen = false;

    public PlantButton(Game game, String plantType, Sprite tileSprite, Rectangle rectangle) {
        super(tileSprite, rectangle, true);
        this.plantType = plantType;
        this.sprite = tileSprite;
        this.game = game;
        rectangle.generateBorder(3, YELLOW);
    }

    @Override
    public void render(RenderHandler renderer, int zoom, Rectangle rectangle) {
        if (sprite != null) {
            if (objectCount > 1) {
                renderer.renderSprite(sprite,
                        region.getX() + rectangle.getX(),
                        region.getY() + rectangle.getY(),
                        zoom, fixed, objectCount);
            } else {
                renderer.renderSprite(sprite,
                        region.getX() + rectangle.getX(),
                        region.getY() + rectangle.getY(),
                        zoom, fixed);
            }
        }
        renderer.renderRectangle(region, rectangle, 1, fixed);
    }

    @Override
    public void activate() {
        game.changeSelectedPlant(plantType);
    }

    @Override
    public void update(Game game) {
        if (plantType.equals(game.getSelectedPlant())) {
            if (!isGreen) {
                region.generateBorder(5, GREEN);
                isGreen = true;
            }
        } else {
            if (isGreen) {
                region.generateBorder(3, YELLOW);
                isGreen = false;
            }
        }
    }

    @Override
    public int getLayer() {
        return 5;
    }
}
