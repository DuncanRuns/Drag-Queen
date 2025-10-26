package me.duncanruns.dragqueen.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @WrapOperation(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setVelocity(DDD)V", ordinal = 2))
    private void noRegularDrag(LivingEntity instance, double x, double y, double z, Operation<Void> original) {
        if (instance instanceof PlayerEntity && ((PlayerEntity) instance).abilities.flying)
            original.call(instance, x, y, z);
        Vec3d velocity = getVelocity();
        original.call(instance, velocity.x, y, velocity.z);
    }

    @WrapOperation(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V", ordinal = 0))
    private void noWaterDrag(LivingEntity instance, Vec3d whatItWants, Operation<Void> original) {
    }

    @WrapOperation(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V", ordinal = 2))
    private void noLavaDrag(LivingEntity instance, Vec3d whatItWants, Operation<Void> original) {
    }

    @WrapOperation(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V", ordinal = 4))
    private void noLavaDrag2(LivingEntity instance, Vec3d whatItWants, Operation<Void> original) {
    }

    @WrapOperation(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V", ordinal = 6))
    private void noElytraDrag(LivingEntity instance, Vec3d whatItWants, Operation<Void> original) {
        original.call(instance, whatItWants.multiply(1 / 0.99F, 1 / 0.98F, 1 / 0.99F));
    }


    @Inject(method = "getVelocityMultiplier", at = @At("HEAD"), cancellable = true)
    private void noSpecialBlockDrag(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(1.0F);
    }
}