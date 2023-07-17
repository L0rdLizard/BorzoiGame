package utilz;

import main.Game;

public class Constants {
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
