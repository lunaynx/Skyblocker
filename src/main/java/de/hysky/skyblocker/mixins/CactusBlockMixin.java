package de.hysky.skyblocker.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import de.hysky.skyblocker.config.SkyblockerConfigManager;
import de.hysky.skyblocker.utils.Utils;
import net.minecraft.block.*;
import net.minecraft.util.shape.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CactusBlock.class)
public abstract class CactusBlockMixin extends Block {
	@Shadow
	protected static VoxelShape SHAPE;

	@Unique
	private static final VoxelShape OLD_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);

	public CactusBlockMixin(Settings settings) {
		super(settings);
	}

	@ModifyReturnValue(method = "getOutlineShape", at = @At("RETURN"))
	private VoxelShape skyblocker$getCactusOutline(VoxelShape original) {
		return Utils.isOnSkyblock() && SkyblockerConfigManager.get().general.hitbox.oldCactusHitbox ? OLD_SHAPE : original;
	}

	@Override
	public VoxelShape getCullingShape(BlockState state) {
		return SHAPE;
	}
}
