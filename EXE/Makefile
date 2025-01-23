# Nombre del archivo JAR y el archivo ZIP
JAR_FILE = recommender_system.jar
ZIP_FILE = archivos.zip
DATA_DIR = ""

# Regla por defecto: Descomprime y ejecuta
all: unzip run

# Regla para descomprimir el archivo ZIP
unzip:
	@echo "Descomprimiendo $(ZIP_FILE)..."
	@unzip -o $(ZIP_FILE) -d $(DATA_DIR)
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
