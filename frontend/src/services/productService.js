const API_BASE_URL = '/api';

export const productService = {
    async getAllProducts() {
        try {
            console.log('Fetching products...');
            const response = await fetch(`${API_BASE_URL}/items?page=1&pageSize=10`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json',
                },
            });
            
            if (!response.ok) {
                const errorData = await response.text();
                console.error('Server response:', errorData);
                throw new Error(`서버 오류: ${response.status}`);
            }
            
            const data = await response.json();
            console.log('Received data:', data);
            
            const items = data.content || [];
            
            return items.map(item => ({
                id: item.id,
                name: item.itemName,
                price: item.itemPrice,
                pricePerGram: Math.floor(item.itemPrice / 4),
                stock: item.itemQuantity,
                imageUrl: item.itemImage || '/coffee-default.jpg'
            }));
        } catch (error) {
            console.error('Error fetching products:', error);
            throw error;
        }
    },

    async getProductById(id) {
        const response = await fetch(`${API_BASE_URL}/items/${id}`);
        if (!response.ok) {
            throw new Error('상품을 불러오는데 실패했습니다.');
        }
        return response.json();
    }
}; 