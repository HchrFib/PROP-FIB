# Nombre del archivo JAR y el archivo 7z
JAR_FILE = recommender_system.jar
ARCHIVE_FILE = archivos.7z
DATA_DIR = archivos # Carpeta de destino predeterminada

# Regla por defecto: Descomprime y ejecuta
all: extract run

# Regla para descomprimir el archivo 7z
extract:
	@echo "Descomprimiendo $(ARCHIVE_FILE) en $(DATA_DIR)..."
	@mkdir -p $(DATA_DIR) # Crea la carpeta si no existe
	@7z x $(ARCHIVE_FILE) -aoa $(DATA_DIR)
	@echo "Archivos descomprimidos en $(DATA_DIR)."

# Regla para ejecutar el archivo JAR
run:
	@echo "Ejecutando $(JAR_FILE)..."
	@java -jar $(JAR_FILE)

# Limpiar los archivos descomprimidos
clean:
	@echo "Eliminando carpeta $(DATA_DIR)..."
	@rm -rf $(DATA_DIR)
	@echo "Carpeta eliminada."

