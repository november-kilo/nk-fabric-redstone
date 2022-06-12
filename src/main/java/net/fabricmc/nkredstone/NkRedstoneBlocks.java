package net.fabricmc.nkredstone;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.nkredstone.block.impl.AndGateBlock;
import net.fabricmc.nkredstone.block.impl.OrGateBlock;
import net.fabricmc.nkredstone.block.impl.VariableEmitterBlock;
import net.fabricmc.nkredstone.block.impl.VariableInverterBlock;
import net.fabricmc.nkredstone.block.impl.VariableResistorBlock;
import net.fabricmc.nkredstone.block.impl.XorGateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class NkRedstoneBlocks {
    public static final Map<String, Block> BLOCKS;

    static {
        BLOCKS = new HashMap<>();
        BLOCKS.put("and_gate", new AndGateBlock(FabricBlockSettings.copyOf(Blocks.REPEATER)));
        BLOCKS.put("or_gate", new OrGateBlock(FabricBlockSettings.copyOf(Blocks.REPEATER)));
        BLOCKS.put("xor_gate", new XorGateBlock(FabricBlockSettings.copyOf(Blocks.REPEATER)));
        BLOCKS.put("variable_emitter", new VariableEmitterBlock(FabricBlockSettings.copyOf(Blocks.REPEATER)));
        BLOCKS.put("variable_inverter", new VariableInverterBlock(FabricBlockSettings.copyOf(Blocks.REPEATER)));
        BLOCKS.put("variable_resistor", new VariableResistorBlock(FabricBlockSettings.copyOf(Blocks.REPEATER)));
    }

    public static void init() {
        BLOCKS.forEach((name, block) -> {
            Identifier identifier = new NkRedstoneModIdentifier(name);
            BlockItem blockItem = new BlockItem(block, new FabricItemSettings().group(ItemGroup.REDSTONE));

            Registry.register(Registry.ITEM, identifier, blockItem);
            Registry.register(Registry.BLOCK, identifier, block);
        });
    }
}
