package badasintended.megane.tooltip.component;

import badasintended.megane.Megane;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.RenderableTextComponent;
import net.minecraft.block.BlockState;
import net.minecraft.block.ComposterBlock;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;

import java.util.List;

import static badasintended.megane.util.MeganeUtils.config;
import static badasintended.megane.util.MeganeUtils.key;

public class ComposterComponent implements IComponentProvider {

    public static final ComposterComponent INSTANCE = new ComposterComponent();

    private static final CompoundTag TAG = new CompoundTag();

    static {
        TAG.putInt(key("color"), 0xFF915118);
    }

    @Override
    public void appendBody(List<Text> tooltip, IDataAccessor accessor, IPluginConfig config) {
        if (!config().fluid.isEnabled()) return;

        BlockState state = accessor.getBlockState();
        int level = state.get(ComposterBlock.LEVEL);
        TAG.putDouble(key("filled"), level);
        TAG.putDouble(key("max"), 7);
        TAG.putString(key("prefix"), I18n.translate("waila.megane.level"));
        TAG.putString(key("text"), String.valueOf(level));
        tooltip.add(new RenderableTextComponent(Megane.BAR, TAG));
    }

}
