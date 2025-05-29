#Programa Tabla Hash - No implementar Map, Set, HashTable, entre otros
#Funciones:
    #1. Calcular el Hash para un dato de tipo entero
    #2. Calcular el hash para un dato de tipo String
    #3. Insertar en Tabla Hash - Clave Entero
    #4. Insertar en Tabla Hash - Clave String
    #5. Buscar x clave
    #6. Borrar x clave
    #7. Mostrar x clave


class TablaHash:
    def __init__(self, tamano=10):
        # Creamos una tabla hash que usa encadenamiento para resolver colisiones
        self.tamano = tamano
        self.buckets = [None] * tamano  # Empezamos con buckets vacíos
        self.num_elementos = 0
    
    def hash_entero(self, clave):
        # Para números enteros usamos el método de división simple
        return clave % self.tamano
    
    def hash_string(self, clave):
        # Para strings usamos un método polinomial que evita muchas colisiones
        if not clave:
            return 0
        
        hash_valor = 0
        base = 37  # Usamos un número primo para distribuir mejor los valores
        
        # Calculamos el hash considerando la posición de cada caracter
        for i, caracter in enumerate(clave):
            hash_valor += ord(caracter) * (base ** (len(clave) - 1 - i))
        
        return hash_valor % self.tamano
    
    def obtener_hash(self, clave):
        # Decidimos qué función usar dependiendo del tipo de datos
        if isinstance(clave, int):
            return self.hash_entero(clave)
        elif isinstance(clave, str):
            return self.hash_string(clave)
        else:
            raise ValueError("Solo acepto números enteros o texto")
    
    def insertar_elemento(self, clave, valor):
        # Agregamos o actualizamos un elemento en la tabla
        indice = self.obtener_hash(clave)
        
        # Si no hay nada en este bucket, creamos una lista nueva
        if self.buckets[indice] is None:
            self.buckets[indice] = []
        
        # Revisamos si ya existe esta clave para actualizarla
        for i, (k, v) in enumerate(self.buckets[indice]):
            if k == clave:
                self.buckets[indice][i] = (clave, valor)
                print(f"Clave '{clave}' actualizada con valor '{valor}'")
                return
        
        # Si es nueva, la agregamos al final del bucket
        self.buckets[indice].append((clave, valor))
        self.num_elementos += 1
        print(f"Insertado: ({clave}, {valor}) en índice {indice}")
    
    def buscar_por_clave(self, clave):
        # Buscamos un elemento por su clave
        indice = self.obtener_hash(clave)
        
        # Si el bucket está vacío, no hay nada que buscar
        if self.buckets[indice] is None:
            return None
        
        # Recorremos el bucket buscando la clave exacta
        for k, v in self.buckets[indice]:
            if k == clave:
                return v
        
        return None  # No encontramos nada
    
    def eliminar_clave(self, clave):
        # Eliminamos un elemento de la tabla
        indice = self.obtener_hash(clave)
        
        # Si el bucket está vacío, no hay nada que eliminar
        if self.buckets[indice] is None:
            return False
        
        # Buscamos el elemento a eliminar
        for i, (k, v) in enumerate(self.buckets[indice]):
            if k == clave:
                elemento_eliminado = self.buckets[indice].pop(i)
                self.num_elementos -= 1
                
                # Si el bucket queda vacío, lo dejamos como None para ahorrar memoria
                if not self.buckets[indice]:
                    self.buckets[indice] = None
                
                print(f"Eliminado: {elemento_eliminado}")
                return True
        
        return False  # No encontramos el elemento
    
    def mostrar_tabla(self):
        # Mostramos todo el contenido de la tabla de forma organizada
        print("\n" + "="*50)
        print("CONTENIDO DE LA TABLA HASH")
        print("="*50)
        
        elementos_totales = 0
        for i in range(self.tamano):
            print(f"Bucket [{i:2d}]: ", end="")
            
            if self.buckets[i] is None:
                print("VACÍO")
            else:
                elementos_en_bucket = len(self.buckets[i])
                elementos_totales += elementos_en_bucket
                
                # Mostramos todos los elementos del bucket conectados con flechas
                for j, (clave, valor) in enumerate(self.buckets[i]):
                    if j == 0:
                        print(f"({clave}, {valor})", end="")
                    else:
                        print(f" -> ({clave}, {valor})", end="")
                print()
        
        print(f"\nTotal de elementos: {self.num_elementos}")
        print("="*50)


def ejecutar_pruebas():
    """
    Función principal que ejecuta las pruebas de la tabla hash
    """
    print("IMPLEMENTACIÓN DE TABLA HASH")
    print("Creando tabla hash con tamaño 10...\n")
    
    # Crear instancia de la tabla hash
    mi_tabla = TablaHash(10)
    
    # PRUEBAS CON ENTEROS
    print(" PRUEBAS CON CLAVES ENTERAS")
    print("-" * 30)
    
    valores_enteros = [(5, "A"), (6, "B"), (3, "C"), (15, "D"), (25, "E"), (56, "G")]
    
    for clave, valor in valores_enteros:
        print(f"Hash de {clave}: {mi_tabla.obtener_hash(clave)}")
        mi_tabla.insertar_elemento(clave, valor)
    
    mi_tabla.mostrar_tabla()
    
    # Pruebas de búsqueda
    print("\n PRUEBAS DE BÚSQUEDA (ENTEROS)")
    print("-" * 35)
    claves_buscar = [15, 40, 25, 99]
    
    for clave in claves_buscar:
        resultado = mi_tabla.buscar_por_clave(clave)
        estado = f"Encontrado: {resultado}" if resultado else "No encontrado"
        print(f"Buscar {clave}: {estado}")
    
    # Pruebas de eliminación
    print("\n  PRUEBAS DE ELIMINACIÓN")
    print("-" * 25)
    
    print("Eliminando clave 15...")
    exito = mi_tabla.eliminar_clave(15)
    print(f"Resultado: {'Éxito' if exito else 'Fallo'}")
    
    print("\nIntentando eliminar clave 40 (no existe)...")
    exito = mi_tabla.eliminar_clave(40)
    print(f"Resultado: {'Éxito' if exito else 'Fallo'}")
    
    # PRUEBAS CON STRINGS
    print("\n\n PRUEBAS CON CLAVES STRING")
    print("-" * 30)
    
    valores_string = [("ABBA", "H"), ("BABA", "I"), ("CASA", "J"), ("PYTHON", "K")]
    
    for clave, valor in valores_string:
        print(f"Hash de '{clave}': {mi_tabla.obtener_hash(clave)}")
        mi_tabla.insertar_elemento(clave, valor)
    
    mi_tabla.mostrar_tabla()
    
    # Más pruebas de búsqueda con strings
    print("\n PRUEBAS DE BÚSQUEDA (STRINGS)")
    print("-" * 35)
    
    claves_string_buscar = ["ABBA", "BABA", "ADIOS", "PYTHON"]
    
    for clave in claves_string_buscar:
        resultado = mi_tabla.buscar_por_clave(clave)
        estado = f"Encontrado: {resultado}" if resultado else "No encontrado"
        print(f"Buscar '{clave}': {estado}")
    
    # Eliminación con strings
    print("\n  ELIMINACIÓN DE STRINGS")
    print("-" * 25)
    
    print("Eliminando 'BABA'...")
    mi_tabla.eliminar_clave("BABA")
    
    print("Verificando que 'BABA' fue eliminado...")
    resultado = mi_tabla.buscar_por_clave("BABA")
    print(f"Buscar 'BABA': {'Encontrado: ' + resultado if resultado else 'No encontrado (eliminado correctamente)'}")
    
    # Estado final
    print("\n ESTADO FINAL DE LA TABLA")
    mi_tabla.mostrar_tabla()


# Ejecutar el programa
if __name__ == "__main__":
    ejecutar_pruebas()
