package com.smallmanseries.farlandstraveler.mixin.worldgen;

import com.smallmanseries.farlandstraveler.common.worldgen.farlands.FarLands;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import org.spongepowered.asm.mixin.*;

@Mixin(NoiseBasedChunkGenerator.class)
public abstract class NoiseBasedChunkGeneratorMixin {

    @Final
    @Mutable
    @Shadow private Holder<NoiseGeneratorSettings> settings;

    @Unique
    private static final BlockState fLT_Project$AIR = Blocks.AIR.defaultBlockState();//这个仅仅是用来让编译器不再弹出气泡


    /*
    private ChunkAccess doFill(Blender blender, StructureManager structureManager, RandomState random, ChunkAccess chunk, int minCellY, int cellCountY) {
        //函数开头，在这里注入创建noiseChunk的语句。可以在farlands类里加一个函数，用于创建noiseChunk，在这里调用。可以模仿下面的getSection函数的写法。
        // <注入点>
        int currentFarlands = 0;
        FarLands[] farlands = FarLands.getFarLandsInChunk(chunk.getPos());//获取当前区块内准备应用的边境之地
        NoiseChunk[] noisechunks = FarLands.createNoiseChunks(chunk);//创建一个noisechunk序列（开始堆屎山）
        // </注入点>

        // NoiseChunk noisechunk = chunk.getOrCreateNoiseChunk(p_224255_ -> this.createNoiseChunk(p_224255_, structureManager, blender, random));//创建noiseChunk（这个语句需要重写）
        //重写：
        NoiseChunk noisechunk = noisechunks[currentFarlands];

        Heightmap heightmap = chunk.getOrCreateHeightmapUnprimed(Heightmap.Types.OCEAN_FLOOR_WG);//创建“OCEAN_FLOOR_WG”高度图
        Heightmap heightmap1 = chunk.getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE_WG);//创建”WORLD_SURFACE_WG“高度图
        ChunkPos chunkpos = chunk.getPos();//获取当前区块的区块坐标

        int minBlockX = chunkpos.getMinBlockX();//获取当前区块最小x坐标
        int minBlockZ = chunkpos.getMinBlockZ();//获取当前区块最小z坐标
        Aquifer aquifer = noisechunk.aquifer();
        noisechunk.initializeForFirstCellX();//初始化noisechunk，这个应该在上面的初始化注入里把所以noisechunk都初始化了
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        int cellWidth = noisechunk.cellWidth();//设置噪声单元宽度
        int cellHeight = noisechunk.cellHeight();//设置噪声单元高度
        int cellCountX = 16 / cellWidth;//设置该区块x方向上噪声单元的个数
        int cellCountZ = 16 / cellWidth;//设置该区块z方向上噪声单元的个数

        for (int currentCellX = 0; currentCellX < cellCountX; currentCellX++) { //开始遍历当前区块x方向上的每一组噪声单元
            noisechunk.advanceCellX(currentCellX);//设置noisechunk准备处理的噪声单元的x坐标

            for (int currentCellZ = 0; currentCellZ < cellCountZ; currentCellZ++) {//开始遍历当前区块z方向上的每一组噪声单元。此时确定了一个“噪声单元柱”
                int numberOfSectionsUnprocessed = chunk.getSectionsCount() - 1;//将当前“噪声单元柱”中还没有处理的区段（高度为16格的一段柱子）数量，设置为当前“噪声单元柱”中包含的区段总数，该值相当于 建筑高度限制➗16，向下取整。
                LevelChunkSection levelchunksection = chunk.getSection(numberOfSectionsUnprocessed);//设置即将处理的区段为本“噪声单元柱”中最顶上的那个区段

                for (int currentCellY = cellCountY - 1; currentCellY >= 0; currentCellY--) {//开始遍历当前“噪声单元柱”y方向上的每一个噪声单元。y方向上的噪声单元数量是从外部传进来的
                    noisechunk.selectCellYZ(currentCellY, currentCellZ);//设置noisechunk准备处理的噪声单元的y坐标和z坐标，至此，noisechunk处理的噪声单元就确定下来了，接下来的工作都会在这一个单元内进行。

                    for (int currentBlockYInCell = cellHeight - 1; currentBlockYInCell >= 0; currentBlockYInCell--) {//开始从最顶层向下一层一层地遍历本噪声单元中的方块
                        int blockY = (minCellY + currentCellY) * cellHeight + currentBlockYInCell;//设置即将处理的方块的y坐标（绝对y坐标）
                        int blockYInCurrentSection = blockY & 15;//设置即将处理的方块在当前区段中的y坐标（相对y坐标）（&15相当于除以16的余数）
                        int currentSection = chunk.getSectionIndex(blockY);//获取即将处理的方块所在的区段
                        if (numberOfSectionsUnprocessed != currentSection) {//如果处理完了一个区段，就将当前“噪声单元柱”中还没有处理的区段数量减一
                            numberOfSectionsUnprocessed = currentSection;
                            levelchunksection = chunk.getSection(currentSection);//使即将处理的区段下降一格（上面那个已经处理完了，所以就把处理用的机器往下移动了一格，这恒河里）
                        }

                        double noisePosY = (double)currentBlockYInCell / cellHeight;//确定当前准备处理的方块在当前噪声单元中的噪声Y坐标
                        noisechunk.updateForY(blockY, noisePosY);//设置noisechunk当前准备处理的方块在当前噪声单元中的噪声Y坐标

                        for (int currentBlockXInCell = 0; currentBlockXInCell < cellWidth; currentBlockXInCell++) {
                            int blockX = minBlockX + currentCellX * cellWidth + currentBlockXInCell;
                            int blockXInCurrentChunk = blockX & 15;
                            double noisePosX = (double)currentBlockXInCell / cellWidth;//确定当前准备处理的方块在当前噪声单元中的噪声X坐标
                            noisechunk.updateForX(blockX, noisePosX);//设置noisechunk当前准备处理的方块在当前噪声单元中的噪声X坐标

                            for (int currentBlockZInCell = 0; currentBlockZInCell < cellWidth; currentBlockZInCell++) {
                                int blockZ = minBlockZ + currentCellZ * cellWidth + currentBlockZInCell;
                                int blockZInCurrentChunk = blockZ & 15;


                                // <注入点>
                                if (farlands[currentFarlands + 1] != null) {
                                    if (farlands[currentFarlands + 1].isCorner()
                                            && (blockX > farlands[currentFarlands + 1].dist() || blockX < -(farlands[currentFarlands + 1].dist()))
                                            && (blockZ > farlands[currentFarlands + 1].dist() || blockZ < -(farlands[currentFarlands + 1].dist()))
                                            || !farlands[currentFarlands + 1].isCorner()
                                            && (blockX > farlands[currentFarlands + 1].dist()
                                            || blockX < -(farlands[currentFarlands + 1].dist())
                                            || blockZ > farlands[currentFarlands + 1].dist()
                                            || blockZ < -(farlands[currentFarlands + 1].dist()))
                                    ) {
                                        //这段用于处理角类边境之地叠加在普通边境之地上的情况。角类边境之地在数组中的排序比普通边境之地靠后。
                                        //也用于处理负坐标的情况
                                        do {
                                            currentFarlands += 1;
                                        } while (farlands[currentFarlands + 1] != null &&
                                            (farlands[currentFarlands + 1].isCorner()
                                                && (blockX > farlands[currentFarlands + 1].dist() || blockX < -(farlands[currentFarlands + 1].dist()))
                                                && (blockZ > farlands[currentFarlands + 1].dist() || blockZ < -(farlands[currentFarlands + 1].dist()))
                                                || !farlands[currentFarlands + 1].isCorner()
                                                && (blockX > farlands[currentFarlands + 1].dist()
                                                || blockX < -(farlands[currentFarlands + 1].dist())
                                                || blockZ > farlands[currentFarlands + 1].dist()
                                                || blockZ < -(farlands[currentFarlands + 1].dist()))
                                            )
                                        );
                                        //更换noisechunk
                                        noisechunk = noisechunks[currentFarlands];
                                        aquifer = noisechunk.aquifer();
                                        this.settings = farlands[currentFarlands].settings();
                                        //重定向方块坐标到新noisechunk的噪声单元，防止新noisechunk的噪声单元大小与原来的不一致（噪声单元大小对应的是NoiseSettings定义文件里的"size_horizontal"、"size_vertical"这两个字段。）
                                        if (cellWidth != noisechunk.cellWidth()) {
                                            cellWidth = noisechunk.cellWidth();
                                            cellCountX = 16 / cellWidth;
                                            cellCountZ = 16 / cellWidth;
                                            currentCellX = blockXInCurrentChunk / cellWidth;
                                            currentCellZ = blockZInCurrentChunk / cellWidth;
                                            currentBlockXInCell = blockXInCurrentChunk % cellWidth;
                                            currentBlockZInCell = blockZInCurrentChunk % cellWidth;
                                        }
                                        //感觉暂时没必要弄y轴的，先加上试试
                                        if (cellHeight != noisechunk.cellHeight()) {
                                            int cellHeightOld = cellHeight;
                                            cellHeight = noisechunk.cellHeight();
                                            currentCellY = (currentCellY * cellHeightOld) / cellHeight;
                                            currentBlockYInCell = blockYInCurrentSection % cellHeight;
                                        }
                                        //更新新换上的这个noisechunk
                                        noisechunk.advanceCellX(currentCellX);
                                        noisechunk.selectCellYZ(currentCellY, currentCellZ);
                                        noisechunk.updateForY(blockY, noisePosY);
                                        noisechunk.updateForX(blockX, noisePosX);
                                        //不用更新z了，下面有。
                                    }
                                }

                                if (currentFarlands > 0) {
                                    if (!farlands[currentFarlands].isCorner()
                                            && (blockX <= farlands[currentFarlands].dist() || blockX >= -(farlands[currentFarlands].dist()))
                                            && (blockZ <= farlands[currentFarlands].dist() || blockZ >= -(farlands[currentFarlands].dist()))
                                            || farlands[currentFarlands].isCorner()
                                            && (blockX <= farlands[currentFarlands].dist()
                                            || blockX >= -(farlands[currentFarlands].dist())
                                            || blockZ <= farlands[currentFarlands].dist()
                                            || blockZ >= -(farlands[currentFarlands].dist()))
                                    ) {
                                        //这段用于处理角类边境之地叠加在普通边境之地上的情况。角类边境之地在数组中的排序比普通边境之地靠后。
                                        //也用于处理负坐标的情况
                                        do {
                                            currentFarlands -= 1;
                                        } while (currentFarlands > 0 &&
                                                (!farlands[currentFarlands].isCorner()
                                                        && (blockX <= farlands[currentFarlands].dist() || blockX >= -(farlands[currentFarlands].dist()))
                                                        && (blockZ <= farlands[currentFarlands].dist() || blockZ >= -(farlands[currentFarlands].dist()))
                                                        || farlands[currentFarlands].isCorner()
                                                        && (blockX <= farlands[currentFarlands].dist()
                                                        || blockX >= -(farlands[currentFarlands].dist())
                                                        || blockZ <= farlands[currentFarlands].dist()
                                                        || blockZ >= -(farlands[currentFarlands].dist()))
                                                )
                                        );
                                        //更换noisechunk
                                        noisechunk = noisechunks[currentFarlands];
                                        aquifer = noisechunk.aquifer();
                                        this.settings = farlands[currentFarlands].settings();
                                        //重定向方块坐标到新noisechunk的噪声单元，防止新noisechunk的噪声单元大小与原来的不一致（噪声单元大小对应的是NoiseSettings定义文件里的"size_horizontal"、"size_vertical"这两个字段。）
                                        if (cellWidth != noisechunk.cellWidth()) {
                                            cellWidth = noisechunk.cellWidth();
                                            cellCountX = 16 / cellWidth;
                                            cellCountZ = 16 / cellWidth;
                                            currentCellX = blockXInCurrentChunk / cellWidth;
                                            currentCellZ = blockZInCurrentChunk / cellWidth;
                                            currentBlockXInCell = blockXInCurrentChunk % cellWidth;
                                            currentBlockZInCell = blockZInCurrentChunk % cellWidth;
                                        }
                                        //感觉暂时没必要弄y轴的，先加上试试
                                        if (cellHeight != noisechunk.cellHeight()) {
                                            int cellHeightOld = cellHeight;
                                            cellHeight = noisechunk.cellHeight();
                                            currentCellY = (currentCellY * cellHeightOld) / cellHeight;
                                            currentBlockYInCell = blockYInCurrentSection % cellHeight;
                                        }
                                        //更新新换上的这个noisechunk
                                        noisechunk.advanceCellX(currentCellX);
                                        noisechunk.selectCellYZ(currentCellY, currentCellZ);
                                        noisechunk.updateForY(blockY, noisePosY);
                                        noisechunk.updateForX(blockX, noisePosX);
                                        //不用更新z了，下面有。
                                    }
                                }

                                // </注入点>

                                double noisePosZ = (double)currentBlockZInCell / cellWidth;//确定当前准备处理的方块在当前噪声单元中的噪声Z坐标
                                noisechunk.updateForZ(blockZ, noisePosZ);//设置noisechunk当前准备处理的方块在当前噪声单元中的噪声Z坐标
                                BlockState blockstate = noisechunk.getInterpolatedState();//给noisechunk输入的数据足够了，开始按照之前输入的数据计算当前方块坐标是空气还是固体
                                if (blockstate == null) {//如果没计算出来，统一填充默认方块（石头）
                                    blockstate = this.settings.value().defaultBlock();
                                }

                                //blockstate = this.debugPreliminarySurfaceLevel(noisechunk, blockX, blockY, blockZ, blockstate);//这一条仅用作调试作用
                                if (blockstate != fLT_Project$AIR && !SharedConstants.debugVoidTerrain(chunk.getPos())) {
                                    levelchunksection.setBlockState(blockXInCurrentChunk, blockYInCurrentSection, blockZInCurrentChunk, blockstate, false);//将正在处理的区段中的指定坐标的方块，设定成前面noisechunk生成的方块状态
                                    heightmap.update(blockXInCurrentChunk, blockY, blockZInCurrentChunk, blockstate);//更新“OCEAN_FLOOR_WG”高度图
                                    heightmap1.update(blockXInCurrentChunk, blockY, blockZInCurrentChunk, blockstate);//更新“WORLD_SURFACE_WG”高度图
                                    if (aquifer.shouldScheduleFluidUpdate() && !blockstate.getFluidState().isEmpty()) {//如果刚刚生成完的这个方块是流体，且需要更新（流出来等），就需要后处理
                                        blockpos$mutableblockpos.set(blockX, blockY, blockZ);
                                        chunk.markPosForPostprocessing(blockpos$mutableblockpos);//将已经生成完的方块标记，以便后处理
                                    }
                                }
                            }
                        }
                    }
                }
            }

            noisechunk.swapSlices();
        }

        noisechunk.stopInterpolation();
        return chunk;
    }

*/
}
