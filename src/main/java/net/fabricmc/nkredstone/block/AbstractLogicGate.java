package net.fabricmc.nkredstone.block;

import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.TickPriority;
import net.minecraft.world.World;

public abstract class AbstractLogicGate extends AbstractRedstoneGateBlock {
    public static final BooleanProperty NEGATE_MODE;
    public static final IntProperty OUTPUT_STRENGTH;

    static {
        NEGATE_MODE = BooleanProperty.of("negate");
        OUTPUT_STRENGTH = IntProperty.of("output_strength", 0, 15);
    }

    public AbstractLogicGate(Settings settings) {
        super(settings);
        setDefaultState(stateManager.getDefaultState().with(NEGATE_MODE, false).with(OUTPUT_STRENGTH, 0));
    }

    public abstract boolean condition(int leftInput, int rightInput);

    @Override
    protected int getUpdateDelayInternal(BlockState state) {
        return 2;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!player.getAbilities().allowModifyWorld) {
            return ActionResult.PASS;
        } else {
            state = state.cycle(NEGATE_MODE);
            world.setBlockState(pos, state, getUpdateDelayInternal(state));
            update(world, pos, state);
            return ActionResult.success(world.isClient);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        update(world, pos, state);
    }

    @Override
    protected int getPower(World world, BlockPos pos, BlockState state) {
        int power = getPowerFromDirection(world, pos, state.get(FACING));
        int leftSide = getPowerFromDirection(world, pos, state.get(FACING).rotateYClockwise());
        int rightSide = getPowerFromDirection(world, pos, state.get(FACING).rotateYCounterclockwise());
        int output = power > 0 && state.get(NEGATE_MODE) != condition(leftSide, rightSide) ? power : 0;
        world.setBlockState(pos, state.with(OUTPUT_STRENGTH, output));
        return output;
    }

    @Override
    protected void updatePowered(World world, BlockPos pos, BlockState state) {
        boolean powered = state.get(POWERED);
        boolean power = hasPower(world, pos, state);

        if (powered != power && !world.getBlockTickScheduler().isTicking(pos, this)) {
            TickPriority tickPriority = TickPriority.HIGH;
            if (this.isTargetNotAligned(world, pos, state)) {
                tickPriority = TickPriority.EXTREMELY_HIGH;
            } else if (powered) {
                tickPriority = TickPriority.VERY_HIGH;
            }

            world.createAndScheduleBlockTick(pos, this, getUpdateDelayInternal(state), tickPriority);
        }
    }

    @Override
    protected int getOutputLevel(BlockView world, BlockPos pos, BlockState state) {
        return state.get(OUTPUT_STRENGTH);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING, POWERED, NEGATE_MODE, OUTPUT_STRENGTH);
    }

    private void update(World world, BlockPos pos, BlockState state) {
        int power = getPower(world, pos, state);
        boolean hasInput = power > 0;
        boolean powered = state.get(POWERED);
        int delay = getUpdateDelayInternal(state);

        if (powered && !hasInput) {
            world.setBlockState(pos, state.with(POWERED, false).with(OUTPUT_STRENGTH, 0), delay);
        } else if (!powered) {
            world.setBlockState(pos, state.with(POWERED, true).with(OUTPUT_STRENGTH, power), delay);
            if (!hasInput) {
                world.createAndScheduleBlockTick(pos, this, delay, TickPriority.VERY_HIGH);
            }
        }

        updateTarget(world, pos, state);
    }

    public int getPowerFromDirection(World world, BlockPos pos, Direction direction) {
        BlockPos blockPos = pos.offset(direction);
        int i = world.getEmittedRedstonePower(blockPos, direction);
        if (i > 14) {
            return 15;
        } else {
            BlockState blockState = world.getBlockState(blockPos);
            int j = blockState.isOf(Blocks.REDSTONE_WIRE) ? blockState.get(RedstoneWireBlock.POWER) : 0;
            if (j > 15) {
                j = 15;
            }
            return Math.max(i, j);
        }
    }
}
