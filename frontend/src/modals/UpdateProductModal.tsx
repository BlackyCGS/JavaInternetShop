import React, { useState, useEffect } from 'react';
import { Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField, Grid } from '@mui/material';
import { Product, Gpu, Motherboard, Cpu, Hdd, Ssd, Ram, Psu, PcCase } from '../types/Product.ts';

interface UpdateProductModalProps {
    open: boolean;
    onClose: () => void;
    product: Product;
    onSave: (updatedProduct: Product) => void;
}

const UpdateProductModal: React.FC<UpdateProductModalProps> = ({ open, onClose, product, onSave }) => {
    const [name, setName] = useState('');
    const [price, setPrice] = useState(0);
    const [stock, setStock] = useState(0);
    const [gpuData, setGpuData] = useState<Gpu | undefined>(undefined);
    const [motherboardData, setMotherboardData] = useState<Motherboard | undefined>(undefined);
    const [cpuData, setCpuData] = useState<Cpu | undefined>(undefined);
    const [hddData, setHddData] = useState<Hdd | undefined>(undefined);
    const [ssdData, setSsdData] = useState<Ssd | undefined>(undefined);
    const [ramData, setRamData] = useState<Ram | undefined>(undefined);
    const [psuData, setPsuData] = useState<Psu | undefined>(undefined);
    const [pcCaseData, setPcCaseData] = useState<PcCase | undefined>(undefined);
    const [loading, setLoading] = useState(false);

    // Initialize form fields based on the provided product
    useEffect(() => {
        if (!product) return;
        setName(product.name);
        setPrice(product.price);
        setStock(product.stock);
        setGpuData(product.gpu || undefined);
        setMotherboardData(product.motherboard || undefined);
        setCpuData(product.cpu || undefined);
        setHddData(product.hdd || undefined);
        setSsdData(product.ssd || undefined);
        setRamData(product.ram || undefined);
        setPsuData(product.psu || undefined);
        setPcCaseData(product.pcCase || undefined);
    }, [product]);

    const handleSave = () => {
        if (loading) return;
        setLoading(true);
        try {
            const updatedProduct: Product = {
                ...product,
                name,
                price,
                stock,
                gpu: gpuData,
                motherboard: motherboardData,
                cpu: cpuData,
                hdd: hddData,
                ssd: ssdData,
                ram: ramData,
                psu: psuData,
                pcCase: pcCaseData,
            };
            onSave(updatedProduct);
            onClose();
        } catch (error) {
            console.error(error);
        } finally {
            setLoading(false);
        }
    };

    // Determine product type based on which field is defined
    const getProductType = () => {
        if (gpuData) return 'gpu';
        if (motherboardData) return 'motherboard';
        if (cpuData) return 'cpu';
        if (hddData) return 'hdd';
        if (ssdData) return 'ssd';
        if (ramData) return 'ram';
        if (psuData) return 'psu';
        if (pcCaseData) return 'pcCase';
        return '';
    };

    const productType = getProductType();

    return (
        <Dialog open={open} onClose={onClose} fullWidth maxWidth="md">
            <DialogTitle>Update Product</DialogTitle>
            <DialogContent>
                <Grid container spacing={2} sx={{ mt: 1 }}>
                    <Grid mx={{xs:"12"}}>
                        <TextField
                            label="Name"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            fullWidth
                        />
                    </Grid>
                    <Grid mx={{xs:"6"}}>
                        <TextField
                            label="Price"
                            type="number"
                            value={price}
                            onChange={(e) => setPrice(parseFloat(e.target.value))}
                            fullWidth
                        />
                    </Grid>
                    <Grid mx={{xs:"6"}}>
                        <TextField
                            label="Stock"
                            type="number"
                            value={stock}
                            onChange={(e) => setStock(parseInt(e.target.value))}
                            fullWidth
                        />
                    </Grid>

                    {/* GPU Fields */}
                    {productType === 'gpu' && gpuData && (
                        <>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Producer"
                                    value={gpuData.producer}
                                    onChange={(e) => setGpuData({ ...gpuData, producer: e.target.value })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="VRAM (GB)"
                                    type="number"
                                    value={gpuData.vram}
                                    onChange={(e) => setGpuData({ ...gpuData, vram: parseInt(e.target.value) })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Boost Clock (MHz)"
                                    type="number"
                                    value={gpuData.boostClock}
                                    onChange={(e) => setGpuData({ ...gpuData, boostClock: parseInt(e.target.value) })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="TDP (W)"
                                    type="number"
                                    value={gpuData.tdp}
                                    onChange={(e) => setGpuData({ ...gpuData, tdp: parseInt(e.target.value) })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="HDMI"
                                    type="number"
                                    value={gpuData.hdmi}
                                    onChange={(e) => setGpuData({ ...gpuData, hdmi: parseInt(e.target.value) })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="DisplayPort"
                                    type="number"
                                    value={gpuData.displayPort}
                                    onChange={(e) => setGpuData({ ...gpuData, displayPort: parseInt(e.target.value) })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="DVI"
                                    type="number"
                                    value={gpuData.dvi}
                                    onChange={(e) => setGpuData({ ...gpuData, dvi: parseInt(e.target.value) })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="VGA"
                                    type="number"
                                    value={gpuData.vga}
                                    onChange={(e) => setGpuData({ ...gpuData, vga: parseInt(e.target.value) })}
                                    fullWidth
                                />
                            </Grid>
                        </>
                    )}

                    {/* Motherboard Fields */}
                    {productType === 'motherboard' && motherboardData && (
                        <>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Producer"
                                    value={motherboardData.producer}
                                    onChange={(e) => setMotherboardData({ ...motherboardData, producer: e.target.value })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Socket"
                                    value={motherboardData.socket}
                                    onChange={(e) => setMotherboardData({ ...motherboardData, socket: e.target.value })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Chipset"
                                    value={motherboardData.chipset}
                                    onChange={(e) => setMotherboardData({ ...motherboardData, chipset: e.target.value })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Form Factor"
                                    value={motherboardData.formFactor}
                                    onChange={(e) => setMotherboardData({ ...motherboardData, formFactor: e.target.value })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Memory Type"
                                    value={motherboardData.memoryType}
                                    onChange={(e) => setMotherboardData({ ...motherboardData, memoryType: e.target.value })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="SATA Ports"
                                    type="number"
                                    value={motherboardData.sata}
                                    onChange={(e) => setMotherboardData({ ...motherboardData, sata: parseInt(e.target.value) })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="RAM Slots"
                                    type="number"
                                    value={motherboardData.ramSlots}
                                    onChange={(e) => setMotherboardData({ ...motherboardData, ramSlots: parseInt(e.target.value) })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="VGA"
                                    type="number"
                                    value={motherboardData.vga}
                                    onChange={(e) => setMotherboardData({ ...motherboardData, vga: parseInt(e.target.value) })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="DVI"
                                    type="number"
                                    value={motherboardData.dvi}
                                    onChange={(e) => setMotherboardData({ ...motherboardData, dvi: parseInt(e.target.value) })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="DisplayPort"
                                    type="number"
                                    value={motherboardData.displayPort}
                                    onChange={(e) => setMotherboardData({ ...motherboardData, displayPort: parseInt(e.target.value) })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="HDMI"
                                    type="number"
                                    value={motherboardData.hdmi}
                                    onChange={(e) => setMotherboardData({ ...motherboardData, hdmi: parseInt(e.target.value) })}
                                    fullWidth
                                />
                            </Grid>
                        </>
                    )}

                    {/* CPU Fields */}
                    {productType === 'cpu' && cpuData && (
                        <>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Producer"
                                    value={cpuData.producer}
                                    onChange={(e) => setCpuData({ ...cpuData, producer: e.target.value })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Socket"
                                    value={cpuData.socket}
                                    onChange={(e) => setCpuData({ ...cpuData, socket: e.target.value })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Cores"
                                    type="number"
                                    value={cpuData.cores}
                                    onChange={(e) => setCpuData({ ...cpuData, cores: parseInt(e.target.value) })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Threads"
                                    type="number"
                                    value={cpuData.threads}
                                    onChange={(e) => setCpuData({ ...cpuData, threads: parseInt(e.target.value) })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="TDP (W)"
                                    type="number"
                                    value={cpuData.tdp}
                                    onChange={(e) => setCpuData({ ...cpuData, tdp: parseInt(e.target.value) })}
                                    fullWidth
                                />
                            </Grid>
                        </>
                    )}

                    {/* HDD Fields */}
                    {productType === 'hdd' && hddData && (
                        <>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Producer"
                                    value={hddData.producer}
                                    onChange={(e) => setHddData({ ...hddData, producer: e.target.value })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Size (GB)"
                                    type="number"
                                    value={hddData.size}
                                    onChange={(e) => setHddData({ ...hddData, size: parseInt(e.target.value) })}
                                    fullWidth
                                />
                            </Grid>
                        </>
                    )}

                    {/* SSD Fields */}
                    {productType === 'ssd' && ssdData && (
                        <>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Producer"
                                    value={ssdData.producer}
                                    onChange={(e) => setSsdData({ ...ssdData, producer: e.target.value })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Form Factor"
                                    value={ssdData.formFactor}
                                    onChange={(e) => setSsdData({ ...ssdData, formFactor: e.target.value })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Protocol"
                                    value={ssdData.protocol}
                                    onChange={(e) => setSsdData({ ...ssdData, protocol: e.target.value })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Size (GB)"
                                    type="number"
                                    value={ssdData.size}
                                    onChange={(e) => setSsdData({ ...ssdData, size: parseInt(e.target.value) })}
                                    fullWidth
                                />
                            </Grid>
                        </>
                    )}

                    {/* RAM Fields */}
                    {productType === 'ram' && ramData && (
                        <>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Producer"
                                    value={ramData.producer}
                                    onChange={(e) => setRamData({ ...ramData, producer: e.target.value })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="RAM Type"
                                    value={ramData.ramType}
                                    onChange={(e) => setRamData({ ...ramData, ramType: e.target.value })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Size (GB)"
                                    type="number"
                                    value={ramData.size}
                                    onChange={(e) => setRamData({ ...ramData, size: parseInt(e.target.value) })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Timings"
                                    value={ramData.timings}
                                    onChange={(e) => setRamData({ ...ramData, timings: e.target.value })}
                                    fullWidth
                                />
                            </Grid>
                        </>
                    )}

                    {/* PSU Fields */}
                    {productType === 'psu' && psuData && (
                        <>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Producer"
                                    value={psuData.producer}
                                    onChange={(e) => setPsuData({ ...psuData, producer: e.target.value })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Watt"
                                    type="number"
                                    value={psuData.watt}
                                    onChange={(e) => setPsuData({ ...psuData, watt: parseInt(e.target.value) })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Size"
                                    value={psuData.size}
                                    onChange={(e) => setPsuData({ ...psuData, size: e.target.value })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Efficiency Rating"
                                    value={psuData.efficiencyRating}
                                    onChange={(e) => setPsuData({ ...psuData, efficiencyRating: e.target.value })}
                                    fullWidth
                                />
                            </Grid>
                        </>
                    )}

                    {/* PC Case Fields */}
                    {productType === 'pcCase' && pcCaseData && (
                        <>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Producer"
                                    value={pcCaseData.producer}
                                    onChange={(e) => setPcCaseData({ ...pcCaseData, producer: e.target.value })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Motherboard Compatibility"
                                    value={pcCaseData.motherboard}
                                    onChange={(e) => setPcCaseData({ ...pcCaseData, motherboard: e.target.value })}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Power Supply Compatibility"
                                    value={pcCaseData.powerSupply}
                                    onChange={(e) => setPcCaseData({ ...pcCaseData, powerSupply: e.target.value })}
                                    fullWidth
                                />
                            </Grid>
                        </>
                    )}
                </Grid>
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose}>Cancel</Button>
                <Button
                    onClick={handleSave}
                    variant="contained"
                    color="primary"
                    disabled={!name || !price}
                >
                    Save
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default UpdateProductModal;