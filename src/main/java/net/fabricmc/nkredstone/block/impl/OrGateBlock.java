package net.fabricmc.nkredstone.block.impl;

import net.fabricmc.nkredstone.block.AbstractLogicGate;

public class OrGateBlock extends AbstractLogicGate {
    public OrGateBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean condition(int leftInput, int rightInput) {
        return Boolean.logicalOr(leftInput > 0, rightInput > 0);
    }
}
