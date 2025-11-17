import axios from 'axios'
import {Product, ReviewRequest} from '../types/Product.ts'

const API_URL = 'api/products'

export const fetchProducts = async (pageNumber = 0, pageSize = 10): Promise<Product[]> => {
    const response = await axios.get(API_URL, {
        params: {
            pageNumber,
            pageSize
        }
    })
    return response.data
}

export const getTotalProducts = async (category : string, name: string ): Promise<number> => {
    const response = await axios.get('api/products/amount', {
        params: {
            category,
            name
        }
    })
    return response.data
}


export const fetchProductById = async (id: number): Promise<Product> => {
    const response = await axios.get(`/api/products/${id}`)
    return response.data
}

export const fetchGpuProducts = async (pageNumber: number, pageSize: number, minPrice: number | undefined,
                                       maxPrice: number | undefined, minBoostClock: number | undefined,
                                       maxBoostClock: number | undefined, minVram: number | undefined
    , maxVram: number | undefined,
                                       minTdp: number | undefined, maxTdp: number | undefined) => {
    const response = await axios.get('/api/products/category/gpu',
        {
            params: {
                    pageNumber,
                    pageSize,
                    minPrice,
                    maxPrice,
                    minBoostClock,
                    maxBoostClock,
                    minVram,
                    maxVram,
                    minTdp,
                    maxTdp

            }
        })
    return response.data
}

export const getTotalGpus = async (minPrice: number | undefined,
                                   maxPrice: number | undefined, minBoostClock: number | undefined,
                                   maxBoostClock: number | undefined, minVram: number | undefined
    , maxVram: number | undefined,
                                   minTdp: number | undefined, maxTdp: number | undefined) => {
    const response = await axios.get('/api/products/category/gpu/amount',
        {
            params: {
                minPrice,
                maxPrice,
                minBoostClock,
                maxBoostClock,
                minVram,
                maxVram,
                minTdp,
                maxTdp

            }
        })
    return response.data
}

export const fetchMotherboardProducts = async (pageNumber: number, pageSize: number,
                                               minPrice: number | undefined, maxPrice: number | undefined,
                                               socket: string | undefined, chipset: string | undefined,
                                               formFactor: string | undefined, memoryType: string | undefined) => {
    const response = await axios.get('/api/products/category/motherboard', {
        params: {
                pageNumber,
                pageSize,
                minPrice,
                maxPrice,
                socket,
                chipset,
                formFactor,
                memoryType
        }
    })
    return response.data
}

export const getTotalMotherboards = async (
                                               minPrice: number | undefined, maxPrice: number | undefined,
                                               socket: string | undefined, chipset: string | undefined,
                                               formFactor: string | undefined, memoryType: string | undefined) => {
    const response = await axios.get('/api/products/category/motherboard/amount', {
        params: {
            minPrice,
            maxPrice,
            socket,
            chipset,
            formFactor,
            memoryType
        }
    })
    return response.data
}

export const fetchPcCaseProducts = async (
    pageNumber: number,
    pageSize: number,
    minPrice?: number,
    maxPrice?: number,
    motherboard?: string,
    powerSupply?: string
) => {
    const response = await axios.get('/api/products/category/pcCase', {
        params: {
            pageNumber,
            pageSize,
            minPrice,
            maxPrice,
            motherboard,
            powerSupply
        }
    })
    return response.data
}

export const getTotalPcCases = async (
    minPrice?: number,
    maxPrice?: number,
    motherboard?: string,
    powerSupply?: string
) => {
    const response = await axios.get('/api/products/category/pcCase/amount', {
        params: {
            minPrice,
            maxPrice,
            motherboard,
            powerSupply
        }
    })
    return response.data
}

export const fetchRamProducts = async (
    pageNumber: number,
    pageSize: number,
    minPrice?: number,
    maxPrice?: number,
    minSize?: number,
    maxSize?: number,
    ramType?: string,
    timings?: string
) => {
    const response = await axios.get('/api/products/category/ram', {
        params: {
            pageNumber,
            pageSize,
            minPrice,
            maxPrice,
            minSize,
            maxSize,
            ramType,
            timings
        }
    })
    return response.data
}

export const getTotalRams = async (
    minPrice?: number,
    maxPrice?: number,
    minSize?: number,
    maxSize?: number,
    ramType?: string,
    timings?: string
) => {
    const response = await axios.get('/api/products/category/ram/amount', {
        params: {
            minPrice,
            maxPrice,
            minSize,
            maxSize,
            ramType,
            timings
        }
    })
    return response.data
}

export const fetchCpuProducts = async (
    pageNumber: number,
    pageSize: number,
    minPrice?: number,
    maxPrice?: number,
    minCores?: number,
    maxCores?: number,
    minThreads?: number,
    maxThreads?: number,
    minTdp?: number,
    maxTdp?: number,
    socket?: string
) => {
    const response = await axios.get('/api/products/category/cpu', {
        params: {
            pageNumber,
            pageSize,
            minPrice,
            maxPrice,
            minCores,
            maxCores,
            minThreads,
            maxThreads,
            minTdp,
            maxTdp,
            socket
        }
    })
    return response.data
}

export const getTotalCpus = async (
    minPrice?: number,
    maxPrice?: number,
    minCores?: number,
    maxCores?: number,
    minThreads?: number,
    maxThreads?: number,
    minTdp?: number,
    maxTdp?: number,
    socket?: string
) => {
    const response = await axios.get('/api/products/category/cpu/amount', {
        params: {
            minPrice,
            maxPrice,
            minCores,
            maxCores,
            minThreads,
            maxThreads,
            minTdp,
            maxTdp,
            socket
        }
    })
    return response.data
}

export const fetchPsuProducts = async (
    pageNumber: number,
    pageSize: number,
    minPrice?: number,
    maxPrice?: number,
    minWatt?: number,
    maxWatt?: number,
    size?: string,
    efficiencyRating?: string
) => {
    const response = await axios.get('/api/products/category/psu', {
        params: {
            pageNumber,
            pageSize,
            minPrice,
            maxPrice,
            minWatt,
            maxWatt,
            size,
            efficiencyRating
        }
    })
    return response.data
}

export const getTotalPsus = async (
    minPrice?: number,
    maxPrice?: number,
    minWatt?: number,
    maxWatt?: number,
    size?: string,
    efficiencyRating?: string
) => {
    const response = await axios.get('/api/products/category/psu/amount', {
        params: {
            minPrice,
            maxPrice,
            minWatt,
            maxWatt,
            size,
            efficiencyRating
        }
    })
    return response.data
}

export const fetchHddProducts = async (
    pageNumber: number,
    pageSize: number,
    minPrice?: number,
    maxPrice?: number,
    minSize?: number,
    maxSize?: number
) => {
    const response = await axios.get('/api/products/category/hdd', {
        params: {
            pageNumber,
            pageSize,
            minPrice,
            maxPrice,
            minSize,
            maxSize
        }
    })
    return response.data
}

export const getTotalHdds = async (
    minPrice?: number,
    maxPrice?: number,
    minSize?: number,
    maxSize?: number
) => {
    const response = await axios.get('/api/products/category/hdd/amount', {
        params: {
            minPrice,
            maxPrice,
            minSize,
            maxSize
        }
    })
    return response.data
}

export const fetchSsdProducts = async (
    pageNumber: number,
    pageSize: number,
    minPrice?: number,
    maxPrice?: number,
    minSize?: number,
    maxSize?: number,
    protocol?: string,
    formFactor?: string
) => {
    const response = await axios.get('/api/products/category/ssd', {
        params: {
            pageNumber,
            pageSize,
            minPrice,
            maxPrice,
            minSize,
            maxSize,
            protocol,
            formFactor
        }
    })
    return response.data
}

export const getTotalSsds = async (
    minPrice?: number,
    maxPrice?: number,
    minSize?: number,
    maxSize?: number,
    protocol?: string,
    formFactor?: string
) => {
    const response = await axios.get('/api/products/category/ssd/amount', {
        params: {
            minPrice,
            maxPrice,
            minSize,
            maxSize,
            protocol,
            formFactor
        }
    })
    return response.data
}

export const fetchProductsByCategory = async (
    category: string,
    pageNumber = 0,
    pageSize = 20,
    filters: Record<string, any> = {}
): Promise<Product[]> => {

    const response = await axios.get(`/api/products/category/${category}`, {
        params: {
            pageNumber,
            pageSize,
            ...filters
        }
    })

    return response.data
}

// универсальная функция получения количества товаров
export const getTotalProductsByCategory = async (
    category: string,
    filters: Record<string, any> = {}
): Promise<number> => {

    const response = await axios.get(`/api/products/category/${category}/amount`, {
        params: {
            ...filters
        }
    })

    return response.data
}


export const updateProduct = async (product: Product): Promise<void> => {
    await axios.put('/api/products/', product, {

    })
}

export const createProduct = async (product: Product): Promise<void> => {
    await axios.post('/api/products', product, {

    })
}

export const deleteProduct = async (id: number): Promise<void> => {
    await axios.delete(`/api/products/delete/${id}`)
}

export const addReview = async (productId: number, review: ReviewRequest): Promise<Product> => {
    const response = await axios.post(
        `/api/products/review`,
        review, // Send ReviewRequest directly in the body
        {
            params: { productId }, // Send productId as query parameter
            withCredentials: true, // Include JWT cookie
            headers: {
                'Content-Type': 'application/json',
            },
        }
    );
    return response.data;
};

export const deleteReview = async (reviewId: number, username: string): Promise<void> => {
    await axios.delete(`/api/products/review`, {
        params: { id: reviewId, name: username },
        withCredentials: true,
    });
};

export const fetchSearchedProducts = async (pageNumber: number, pageSize: number, name: string) => {
    const response = await axios.get('/api/products/name', {
        params: {
            pageNumber,
            pageSize,
            name
        }
    }).catch((error) => {
        if(error.response.status === 404) {
            throw error.response.data.message
        }
    })
    return response?.data
}
