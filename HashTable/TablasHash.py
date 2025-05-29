TAMANO_TABLA = 10
tabla_hash = [None] * TAMANO_TABLA
num_elementos = 0

def hash_entero(clave):
    # División simple
    return clave % TAMANO_TABLA

def hash_string(clave):
    # Para strings usamos un método polinomial que evita muchas colisiones
    if not clave:
        return 0
    
    hash_valor = 0
    base = 37  # Usamos un número primo para distribuir mejor los valores
    
    # Calculamos el hash considerando la posición de cada caracter
    for i, caracter in enumerate(clave):
        hash_valor += ord(caracter) * (base ** (len(clave) - 1 - i))
    
    return hash_valor % TAMANO_TABLA

def calcular_hash(clave):
    # Decidimos qué función usar dependiendo del tipo de datos
    if isinstance(clave, int):
        return hash_entero(clave)
    elif isinstance(clave, str):
        return hash_string(clave)
    else:
        raise ValueError("Solo acepto números enteros o texto")

def insertar_elemento(clave, valor):
    global num_elementos
    indice = calcular_hash(clave)
    
    if tabla_hash[indice] is None:
        tabla_hash[indice] = []
    
    # Revisamos si ya existe esta clave para actualizarla
    for i, (k, v) in enumerate(tabla_hash[indice]):
        if k == clave:
            tabla_hash[indice][i] = (clave, valor)
            print(f"Clave '{clave}' actualizada con valor '{valor}'")
            return
    
    # Si es nueva, la agregamos al final del bucket
    tabla_hash[indice].append((clave, valor))
    num_elementos += 1
    print(f"Insertado: ({clave}, {valor}) en índice {indice}")

def buscar_elemento(clave):
    indice = calcular_hash(clave)
    
    if tabla_hash[indice] is None:
        return None
    
    for k, v in tabla_hash[indice]:
        if k == clave:
            return v
    
    return None  

def eliminar_elemento(clave):
    global num_elementos
    indice = calcular_hash(clave)
    
    if tabla_hash[indice] is None:
        return False
    
    # Buscamos el elemento a eliminar
    for i, (k, v) in enumerate(tabla_hash[indice]):
        if k == clave:
            elemento_eliminado = tabla_hash[indice].pop(i)
            num_elementos -= 1
            
            # Si el bucket queda vacío, lo dejamos como None para ahorrar memoria
            if not tabla_hash[indice]:
                tabla_hash[indice] = None
            
            print(f"Eliminado: {elemento_eliminado}")
            return True
    
    return False  

def mostrar_tabla():
    print("\n" + "="*50)
    print("CONTENIDO DE LA TABLA HASH")
    print("="*50)
    
    for i in range(TAMANO_TABLA):
        print(f"Bucket [{i:2d}]: ", end="")
        
        if tabla_hash[i] is None:
            print("VACÍO")
        else:
            # Mostramos todos los elementos del bucket conectados con flechas
            for j, (clave, valor) in enumerate(tabla_hash[i]):
                if j == 0:
                    print(f"({clave}, {valor})", end="")
                else:
                    print(f" -> ({clave}, {valor})", end="")
            print()
    
    print(f"\nTotal de elementos: {num_elementos}")
    print("="*50)

def limpiar_tabla():
    global tabla_hash, num_elementos
    tabla_hash = [None] * TAMANO_TABLA
    num_elementos = 0
    print("Tabla limpiada - empezando de nuevo")

def ejecutar_pruebas():
    print("IMPLEMENTACIÓN DE TABLA HASH SIN CLASES")
    print("Creando tabla hash con tamaño 10...\n")
    
    print("PRUEBAS CON CLAVES ENTERAS")
    print("-" * 30)
    
    valores_enteros = [(5, "A"), (6, "B"), (3, "C"), (15, "D"), (25, "E"), (56, "G")]
    
    for clave, valor in valores_enteros:
        print(f"Hash de {clave}: {calcular_hash(clave)}")
        insertar_elemento(clave, valor)
    
    mostrar_tabla()
    
    print("\nPRUEBAS DE BÚSQUEDA CON ENTEROS")
    print("-" * 35)
    claves_buscar = [15, 40, 25, 99]
    
    for clave in claves_buscar:
        resultado = buscar_elemento(clave)
        estado = f"Encontrado: {resultado}" if resultado else "No encontrado"
        print(f"Buscar {clave}: {estado}")
    
    print("\nPRUEBAS DE ELIMINACIÓN")
    print("-" * 25)
    
    print("Eliminando clave 15...")
    exito = eliminar_elemento(15)
    print(f"Resultado: {'Éxito' if exito else 'Fallo'}")
    
    print("\nIntentando eliminar clave 40 que no existe...")
    exito = eliminar_elemento(40)
    print(f"Resultado: {'Éxito' if exito else 'Fallo'}")
    
    print("\n\nPRUEBAS CON CLAVES DE TEXTO")
    print("-" * 30)
    
    valores_string = [("ABBA", "H"), ("BABA", "I"), ("CASA", "J"), ("PYTHON", "K")]
    
    for clave, valor in valores_string:
        print(f"Hash de '{clave}': {calcular_hash(clave)}")
        insertar_elemento(clave, valor)
    
    mostrar_tabla()
    
    print("\nPRUEBAS DE BÚSQUEDA CON TEXTO")
    print("-" * 35)
    
    claves_string_buscar = ["ABBA", "BABA", "ADIOS", "PYTHON"]
    
    for clave in claves_string_buscar:
        resultado = buscar_elemento(clave)
        estado = f"Encontrado: {resultado}" if resultado else "No encontrado"
        print(f"Buscar '{clave}': {estado}")
    
    print("\nELIMINACIÓN DE TEXTO")
    print("-" * 25)
    
    print("Eliminando 'BABA'...")
    eliminar_elemento("BABA")
    
    print("Verificando que 'BABA' ya no está...")
    resultado = buscar_elemento("BABA")
    print(f"Buscar 'BABA': {'Encontrado: ' + resultado if resultado else 'No encontrado (eliminado correctamente)'}")
    
    print("\nESTADO FINAL DE LA TABLA")
    mostrar_tabla()

if __name__ == "__main__":
    ejecutar_pruebas()
