import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.navtest.domain.model.CartData
import com.example.navtest.domain.model.CartItemData
import com.example.navtest.domain.model.Product
import com.example.navtest.presentation.viewmodel.CartViewModel
import com.example.navtest.ui.components.TopBarGlobal
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun SharedCartDetailsScreen(cartId: String, navController: NavController, cartViewModel: CartViewModel) {
    val cart = remember { mutableStateOf<CartData?>(null) }
    val productsDetails = remember { mutableStateOf<Map<String, Product>>(emptyMap()) } // Para armazenar os produtos carregados

    LaunchedEffect(cartId) {
        val db = FirebaseFirestore.getInstance()
        val cartRef = db.collection("carts")
            .document(cartId)
        cartRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                val cartData = document.toObject(CartData::class.java)
                cart.value = cartData

                val productNames = cartData?.products?.map { it.productId } ?: emptyList()  // Mudei para buscar pelos nomes
                productNames.forEach { productName ->
                    db.collection("product")  // Atualiza para a coleção "product"
                        .whereEqualTo("name", productName)  // Buscando pelo nome do produto
                        .get()
                        .addOnSuccessListener { productQuerySnapshot ->
                            if (!productQuerySnapshot.isEmpty) {
                                val product = productQuerySnapshot.documents[0].toObject(Product::class.java)
                                product?.let {
                                    productsDetails.value = productsDetails.value + (productName to it)
                                }
                            }
                        }
                }
            } else {
                cart.value = null
            }
        }
    }

    Scaffold(
        topBar = {
            TopBarGlobal(
                title = "Detalhes do Carrinho",
                navController = navController,
                cartItems = cartViewModel.cartItems,
                showNavigationButton = true
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                if (cart.value == null) {
                    Text("Carrinho não encontrado.")
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(cart.value?.products ?: emptyList()) { cartItem ->
                            val product = productsDetails.value[cartItem.productId]
                            if (product != null) {
                                SharedCartItemDetail(cartItem, product)
                            }
                        }
                    }
                }
            }
        }
    )
}


@Composable
fun SharedCartItemDetail(cartItem: CartItemData, product: Product) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { /* Placeholder para futuras ações, como editar a quantidade */ },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = product.image,
                contentDescription = "Imagem do produto",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold
                )

                Text(
                    text = "Preço: ${product.price} €",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "Quantidade: ${cartItem.quantity}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
