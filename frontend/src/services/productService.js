const API_BASE_URL = '/api';

const products = [
    {
        id: 1,
        name: "Columbia Quindio (400G)",
        imageUrl: "/images/coffee-4.png",
        price: 18000,
        pricePerGram: 4500,
        stock: 100
    },
    {
        id: 2,
        name: "Ethiopia Sidamo (400G)",
        imageUrl: "/images/coffee-3.png",
        price: 22000,
        pricePerGram: 5500,
        stock: 100
    },
    {
        id: 3,
        name: 'Columbia Narino (400G)',
        imageUrl: '/images/coffee-1.png',
        price: 20000,
        pricePerGram: 5000,
        stock: 10,
        weight: '400G'
    },
    {
        id: 4,
        name: 'Brazil Serra Do Caparao (400G)',
        imageUrl: '/images/coffee-2.png',
        price: 16000,
        pricePerGram: 4000,
        stock: 15,
        weight: '400G'
    }
];

export const productService = {
    getAllProducts: async () => {
        // 실제 API 호출을 시뮬레이션
        return new Promise((resolve) => {
            setTimeout(() => {
                resolve(products);
            }, 500);
        });
    },

    async getProductById(id) {
        const response = await fetch(`${API_BASE_URL}/items/${id}`);
        if (!response.ok) {
            throw new Error('상품을 불러오는데 실패했습니다.');
        }
        return response.json();
    }
}; 