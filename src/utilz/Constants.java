package utilz;

import main.Game;

public class Constants {

    public static final float GRAVITY = 0.04f * Game.SCALE;
    public static final int ANI_SPEED = 25;

    public static class ObjectConstants {

//        public static final int RED_POTION = 0;
//        public static final int BLUE_POTION = 1;
//        public static final int BARREL = 2;
//        public static final int BOX = 3;
        public static final int COIN = 0;
        public static final int COIN2 = 0;
        public static final int COIN3 = 0;

//        public static final int RED_POTION_VALUE = 15;
//        public static final int BLUE_POTION_VALUE = 10;

//        public static final int CONTAINER_WIDTH_DEFAULT = 40;
//        public static final int CONTAINER_HEIGHT_DEFAULT = 30;
//        public static final int CONTAINER_WIDTH = (int) (Game.SCALE * CONTAINER_WIDTH_DEFAULT);
//        public static final int CONTAINER_HEIGHT = (int) (Game.SCALE * CONTAINER_HEIGHT_DEFAULT);
        public static final int COIN_WIDTH_DEFAULT = 12;
        public static final int COIN_HEIGHT_DEFAULT = 16;
        public static final int COIN_WIDTH = (int) (COIN_WIDTH_DEFAULT * Game.SCALE);
        public static final int COIN_HEIGHT = (int) (COIN_HEIGHT_DEFAULT * Game.SCALE);

//        public static final int POTION_WIDTH_DEFAULT = 12;
//        public static final int POTION_HEIGHT_DEFAULT = 16;
//        public static final int POTION_WIDTH = (int) (Game.SCALE * POTION_WIDTH_DEFAULT);
//        public static final int POTION_HEIGHT = (int) (Game.SCALE * POTION_HEIGHT_DEFAULT);

        public static int GetSpriteAmount(int object_type) {
            switch (object_type) {
                case COIN :
                    return 4;
            }
            return 1;
        }
    }
    public static class EnemyConstants{
        public static final int BALL = 0;

        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 4;
        public static final int HIT = 3;
        public static final int DEAD = 2;

        public static final int BALL_WIDTH_DEFAULT = 80;
        public static final int BALL_HEIGHT_DEFAULT = 64;

        public static final int BALL_WIDTH = (int) (BALL_WIDTH_DEFAULT * Game.SCALE);
        public static final int BALL_HEIGHT = (int) (BALL_HEIGHT_DEFAULT * Game.SCALE);

        public static final int BALL_DRAWOFFSET_X = (int) (36 * Game.SCALE);
        public static final int BALL_DRAWOFFSET_Y = (int) (29 * Game.SCALE);

        public static int GetSpriteAmount(int enemy_type, int enemy_state){
            switch (enemy_type){
                case BALL:
                    switch (enemy_state){
                        case IDLE:
                            return 4;
                        case RUNNING:
                            return 4;
                        case ATTACK:
                            return 6;
                    }
            }
            return 0;
        }
        public static int GetMaxHP(int enemy_type){
            switch (enemy_type){
                case BALL:
                    return 2;
                default:
                    return 1;
            }
        }

        public static int GetEnemyDmg(int enemy_type){
            switch (enemy_type){
                case BALL:
                    return 1;
                default:
                    return 0;
            }
        }
    }
    public static class UI{
        public static class Buttons{
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
        }
        public static class PauseButtons {
            public static final int SOUND_SIZE_DEFAULT = 42;
            public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);
        }

        public static class URMButtons {
            public static final int URM_DEFAULT_SIZE = 56;
            public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.SCALE);

        }
    }
    public static class Directions{
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;

    }
    public static class PlayerConstants{
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMP = 2;
        public static final int DROP = 3;
        public static final int ATTACK = 4;
        public static int GetSpriteAmount(int player_action){
            switch (player_action){
                case IDLE, RUNNING -> {
                    return 4;
                }
                case JUMP -> {
                    return 5;
                }
                case DROP -> {
                    return 1;
                }
                case ATTACK -> {
                    return 6;
                }
                default -> {
                    return 2;
                }
            }
        }
    }
}
