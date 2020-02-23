package org.fibers.apps.dtree.ui.shape;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Important Note: For Pane instances always use prefWidth/prefHeight instead of
 * width and height properties.
 *
 * @author Sabbagh
 */
public class AbstractNode extends Pane {

    private double margin = 1.0;

    // Handles
    private static final int handleSize = 6;
    private Paint handleColor = Color.RED;
    private Rectangle topLeftHandle;
    private Rectangle topHandle;
    private Rectangle topRightHandle;
    private Rectangle leftHandle;
    private Rectangle rightHandle;
    private Rectangle bottomLeftHandle;
    private Rectangle bottomHandle;
    private Rectangle bottomRightHandle;

    // Header
    private String titleStr;

    private final SimpleObjectProperty<AbstractNode> selectedProperty;
    private Pane content;

    public AbstractNode(SimpleObjectProperty<AbstractNode> selectedProperty) {
        this.selectedProperty = selectedProperty;
        createNode();
    }

    // todo make abstract
    protected Pane createContent() {
        Pane pane = new Pane();
//        pane.setPrefWidth(100);
//        pane.setPrefHeight(100);
        pane.setStyle("-fx-background-color: yellow");
        pane.setOpacity(0.5);
        return pane;
    }

    private void createNode() {
        content = createContent();
        setEventHandlers();

        //createHandles();
        //this.getChildren().addAll(content, topLeftHandle, topHandle, topRightHandle, leftHandle, rightHandle, bottomLeftHandle, bottomHandle, bottomRightHandle);
        this.getChildren().addAll(content);
    }

    private void setEventHandlers() {
        this.selectedProperty.addListener((observable, oldValue, newValue) -> {
            if (oldValue == AbstractNode.this) {
                renderUnSelected();
            }

            if (newValue == AbstractNode.this) {
                renderSelected();
            }
        });

        // todo has no effect
        this.setOnMouseClicked(event -> {
            AbstractNode.this.selectedProperty.set(AbstractNode.this);
        });

        content.setOnMouseClicked(event -> {
            AbstractNode.this.selectedProperty.set(AbstractNode.this);
        });

        /**
         * Mark mouse position for drag, resize, move, etc.
         */
        content.setOnMousePressed(event -> {
            System.out.println("on mouse pressed");
            oldX = event.getSceneX();
            oldY = event.getSceneY();

            AbstractNode.this.toFront();
        });

        content.setOnMouseReleased(event -> content.setCursor(Cursor.DEFAULT));

        content.setOnMouseDragged(event -> {
            System.out.println("Mouse dragged ... " + " screenX: " + event.getScreenX() + " sceneX: " + event.getSceneX() + " X: " + event.getX());

            this.setCursor(Cursor.MOVE);
            AbstractNode.this.setLayoutX(getLayoutX() + (event.getSceneX() - oldX));
            AbstractNode.this.setLayoutY(getLayoutY() + (event.getSceneY() - oldY));

            oldX = event.getSceneX();
            oldY = event.getSceneY();
        });

        this.widthProperty().addListener((observable, oldValue, newValue) -> {
            // todo move to layout content
            content.setPrefWidth(AbstractNode.this.getWidth() - 2 * margin);
            //layoutHandles();
        });

        this.heightProperty().addListener((observable, oldValue, newValue) -> {
            // todo move to layout content
            /**
             * Content needs to be updated first, because some handle
             * positions depend on it.
             */
            content.setPrefHeight(AbstractNode.this.getHeight() - 2 * margin);
            //layoutHandles();
//            double titleHeight = JavafxUtil.findTextSize(title.getText(), title.getFont()).getHeight();
//            title.setLayoutY(margin + headerHeight / 2 - titleHeight / 2 - title.getBaselineOffset() / 2);
        });
    }

    private void createHandles() {
        // 1- Create Handles
        topLeftHandle = new Rectangle(0, 0, handleSize, handleSize);
        topHandle = new Rectangle(0, 0, handleSize, handleSize);
        topRightHandle = new Rectangle(0, 0, handleSize, handleSize);
        leftHandle = new Rectangle(0, 0, handleSize, handleSize);
        rightHandle = new Rectangle(0, 0, handleSize, handleSize);
        bottomLeftHandle = new Rectangle(0, 0, handleSize, handleSize);
        bottomHandle = new Rectangle(0, 0, handleSize, handleSize);
        bottomRightHandle = new Rectangle(0, 0, handleSize, handleSize);

        // 2- Set Handles Cursors
        topLeftHandle.setCursor(Cursor.NW_RESIZE);
        topHandle.setCursor(Cursor.N_RESIZE);
        topRightHandle.setCursor(Cursor.NE_RESIZE);
        leftHandle.setCursor(Cursor.E_RESIZE);
        rightHandle.setCursor(Cursor.W_RESIZE);
        bottomLeftHandle.setCursor(Cursor.SW_RESIZE);
        bottomHandle.setCursor(Cursor.S_RESIZE);
        bottomRightHandle.setCursor(Cursor.SE_RESIZE);

        // 3- Paint Handles
        topLeftHandle.setFill(handleColor);
        topHandle.setFill(handleColor);
        topRightHandle.setFill(handleColor);
        leftHandle.setFill(handleColor);
        rightHandle.setFill(handleColor);
        bottomLeftHandle.setFill(handleColor);
        bottomHandle.setFill(handleColor);
        bottomRightHandle.setFill(handleColor);

        // 4- Layout Handles in the Parent Pane
        // See parent pane width and height change listeners
        /**
         * ********************************************
         *
         * 5- Set Resizers Based on Handle Drag Events.
         *
         * *********************************************
         */
        topLeftHandle.setOnMouseDragged(event -> {
            double displaceWidth = event.getSceneX() - oldX;
            double displaceHeight = event.getSceneY() - oldY;

            double oldWidth = AbstractNode.this.getPrefWidth();
            double oldHeight = AbstractNode.this.getPrefHeight();
            AbstractNode.this.setPrefWidth(AbstractNode.this.getPrefWidth() - displaceWidth);
            AbstractNode.this.setPrefHeight(AbstractNode.this.getPrefHeight() - displaceHeight);
            // fix parent pane position
            AbstractNode.this.setLayoutX(AbstractNode.this.getLayoutX() - (AbstractNode.this.getPrefWidth() - oldWidth));
            AbstractNode.this.setLayoutY(AbstractNode.this.getLayoutY() - (AbstractNode.this.getPrefHeight() - oldHeight));

            oldX = event.getSceneX();
            oldY = event.getSceneY();
        });

        topHandle.setOnMouseDragged(event -> {
            // FIXME checking size an policies must be done on its separate methods
            //  if (getScaleY() < 0.3) return;
            double displace = event.getSceneY() - oldY;

            double oldHeight = AbstractNode.this.getPrefHeight();
            AbstractNode.this.setPrefHeight(AbstractNode.this.getPrefHeight() - displace);
            /**
             * Note: The following code is buggy! It doesn't fix parent's position based on the mouse new position:
             * <p/>
             * {@code CropBox.this.setLayoutY(event.getSceneY() - margin - handleSize / 2);}
             */
            AbstractNode.this.setLayoutY(AbstractNode.this.getLayoutY() - (AbstractNode.this.getPrefHeight() - oldHeight));

            oldY = event.getSceneY();
        });

        topRightHandle.setOnMouseDragged(event -> {
            double displaceWidth = event.getSceneX() - oldX;
            double displaceHeight = event.getSceneY() - oldY;

            double oldHeight = AbstractNode.this.getPrefHeight();
            // set parent size
            AbstractNode.this.setPrefWidth(AbstractNode.this.getPrefWidth() + displaceWidth);
            AbstractNode.this.setPrefHeight(AbstractNode.this.getPrefHeight() - displaceHeight);
            // update parent position
            AbstractNode.this.setLayoutY(AbstractNode.this.getLayoutY() - (AbstractNode.this.getPrefHeight() - oldHeight));

            oldX = event.getSceneX();
            oldY = event.getSceneY();
        });

        leftHandle.setOnMouseDragged(event -> {
            double displaceWidth = event.getSceneX() - oldX;

            double oldWidth = AbstractNode.this.getPrefWidth();
            // set parent size
            AbstractNode.this.setPrefWidth(AbstractNode.this.getPrefWidth() - displaceWidth);
            // update parent position
            AbstractNode.this.setLayoutX(AbstractNode.this.getLayoutX() - (AbstractNode.this.getPrefWidth() - oldWidth));

            oldX = event.getSceneX();
        });

        rightHandle.setOnMouseDragged(event -> {
            double displaceWidth = event.getSceneX() - oldX;

            AbstractNode.this.setPrefWidth(AbstractNode.this.getPrefWidth() + displaceWidth);
            // default behavior in JavaFX, no need to fix pane position

            oldX = event.getSceneX();
        });

        bottomLeftHandle.setOnMouseDragged(event -> {
            double displaceWidth = event.getSceneX() - oldX;
            double displaceHeight = event.getSceneY() - oldY;

            double oldWidth = AbstractNode.this.getPrefWidth();
            // set parent size
            AbstractNode.this.setPrefWidth(AbstractNode.this.getPrefWidth() - displaceWidth);
            AbstractNode.this.setPrefHeight(AbstractNode.this.getPrefHeight() + displaceHeight);
            // update parent position
            AbstractNode.this.setLayoutX(AbstractNode.this.getLayoutX() - (AbstractNode.this.getPrefWidth() - oldWidth));

            oldX = event.getSceneX();
            oldY = event.getSceneY();
        });

        bottomHandle.setOnMouseDragged(event -> {
            double displace = event.getSceneY() - oldY;

            // update parent position
            AbstractNode.this.setPrefHeight(AbstractNode.this.getPrefHeight() + displace);

            oldY = event.getSceneY();
        });

        bottomRightHandle.setOnMouseDragged(event -> {
            double displaceWidth = event.getSceneX() - oldX;
            double displaceHeight = event.getSceneY() - oldY;

            // set parent size
            AbstractNode.this.setPrefWidth(AbstractNode.this.getPrefWidth() + displaceWidth);
            AbstractNode.this.setPrefHeight(AbstractNode.this.getPrefHeight() + displaceHeight);
            // no need to fix parent pane position

            oldX = event.getSceneX();
            oldY = event.getSceneY();
        });
    }

    private void layoutHandles() {
        double parentWidth = this.widthProperty().doubleValue();
        double parentHeight = this.heightProperty().doubleValue();

        topLeftHandle.setLayoutX(margin - handleSize / 2);
        topLeftHandle.setLayoutY(margin - handleSize / 2);

        topHandle.setLayoutX(parentWidth / 2 - handleSize / 2); // layout the handle at the center
        topHandle.setLayoutY(margin - handleSize / 2);

        topRightHandle.setLayoutX(parentWidth - margin - handleSize / 2);
        topRightHandle.setLayoutY(margin - handleSize / 2);

        leftHandle.setLayoutX(margin - handleSize / 2);
        leftHandle.setLayoutY(margin + content.getPrefHeight() / 2 - handleSize / 2);

        rightHandle.setLayoutX(parentWidth - margin - handleSize / 2);
        rightHandle.setLayoutY(margin + content.getPrefHeight() / 2 - handleSize / 2);

        bottomLeftHandle.setLayoutX(margin - handleSize / 2);
        bottomLeftHandle.setLayoutY(parentHeight - margin - handleSize / 2);

        bottomHandle.setLayoutX(parentWidth / 2 - handleSize / 2);
        bottomHandle.setLayoutY(parentHeight - margin - handleSize / 2);

        bottomRightHandle.setLayoutX(parentWidth - margin - handleSize / 2);
        bottomRightHandle.setLayoutY(parentHeight - margin - handleSize / 2);
    }


    private double oldX;
    private double oldY;

    public double getMargin() {
        return margin;
    }

    public void setMargin(double margin) {
        this.margin = margin;
    }

    public String getTitle() {
        return titleStr;
    }

    public void setTitle(String title) {
        this.titleStr = title;
    }

    public boolean isSelected() {
        return selectedProperty.get() == this;
    }

    public void setSelected(boolean selected) {
        if (selected) {
            selectedProperty.set(this);
        } else {
            selectedProperty.set(null);
        }
    }

    private void renderSelected() {
        // add handles
        // register handle actions
        // add style
        /**
         * Set the border.
         * <p>
         * Note: we cannot use {@code Rectangle} node as a border, because
         * setting transparency on it has effect on both its strike and
         * background. In other words, we cannot set {@code Rectangle} stroke and
         * background transparency separately.
         */
        this.setStyle(
                new StringBuilder()
                        //.append("-fx-padding: 10;") // padding between content children, has no effect if content does not have any children.
                        .append("-fx-border-style: dashed inside;") // line style
                        .append("-fx-border-width: 2;")
                        //.append("-fx-border-insets: 5;")
                        //.append("-fx-border-radius: 5;")
                        .append("-fx-border-color: blue;") // line color
                        .toString());
    }

    private void renderUnSelected() {
        // add handles
        // register handle actions
        // add style
        this.setStyle("");
    }

}
