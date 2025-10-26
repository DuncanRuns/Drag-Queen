package me.duncanruns.dragqueen.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FireworkRocketEntity.class)
public abstract class FireworkRocketEntityMixin {
    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V"))
    private void addNotSet(LivingEntity guy, Vec3d vec3d, Operation<Void> original) {
        double speed = MathHelper.sqrt(Math.max(vec3d.lengthSquared(), guy.getVelocity().lengthSquared()));
        original.call(guy, guy.getRotationVector().normalize().multiply(speed + 0.1D));
    }
}
