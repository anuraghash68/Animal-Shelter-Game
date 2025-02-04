package base.gui;

import base.Game;
import base.graphicsservice.Rectangle;
import base.graphicsservice.RenderHandler;
import base.graphicsservice.Sprite;
import base.map.TileService;

import java.util.List;

import static base.constants.MultiOptionalObjects.MASTER_TILE_LIST;
import static base.constants.ColorConstant.GREEN;
import static base.constants.ColorConstant.YELLOW;

public class SDKButton extends GUIButton {

    Game game;
    int tileID;
    boolean isGreen = false;

    public SDKButton(Game game, int tileID, Sprite tileSprite, Rectangle rectangle) {
        super(tileSprite, rectangle, true);
        this.game = game;
        this.tileID = tileID;
        rectangle.generateBorder(3, YELLOW);

        checkIfContainsMultipleOptions(tileID);
    }

    private void checkIfContainsMultipleOptions(int tileID) {
        for (List<Integer> list : MASTER_TILE_LIST) {
            if (list.contains(tileID)) {
                multipleOptions = true;
                break;
            }
        }
    }

    @Override
    public void render(RenderHandler renderer, int zoom, Rectangle interfaceRect) {
        if (sprite != null) {
            if (multipleOptions) {
                renderer.renderSprite(sprite,
                        region.getX() + interfaceRect.getX(),
                        region.getY() + interfaceRect.getY(), zoom, fixed, "<      >");
            } else if (objectCount > 1) {
                renderer.renderSprite(sprite,
                        region.getX() + interfaceRect.getX(),
                        region.getY() + interfaceRect.getY(), zoom, fixed, objectCount);
            } else {
                renderer.renderSprite(sprite,
                        region.getX() + interfaceRect.getX(),
                        region.getY() + interfaceRect.getY(),
                        zoom,
                        fixed);
            }
        }
        renderer.renderRectangle(region, interfaceRect, 1, fixed);
    }

    public void activate() {
        int nextId = guiService.getNextId(tileID);
        if (isGreen && nextId != tileID) {
            tileID = nextId;
            sprite = new TileService().getTiles().get(tileID).getSprite();
        }
        game.changeTile(tileID);
    }

    @Override
    public void update(Game game) {
        if (tileID == game.getSelectedTileId()) {
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