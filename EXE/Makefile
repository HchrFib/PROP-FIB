# Nombre de los archivos JAR
JAR_TERMINAL = recommender_system_terminal.jar
JAR_GUI = recommender_system_GUI.jar
ARCHIVE_FILE = archivos.7z
DATA_DIR = archivos # Carpeta de destino predeterminada

# Regla por defecto: Descomprime y ejecuta
all: extract choose_run

# Regla para descomprimir el archivo 7z
extract:
	@echo "Descomprimiendo $(ARCHIVE_FILE) en $(DATA_DIR)..."
	@mkdir -p $(DATA_DIR) # Crea la carpeta si no existe
	@7z x $(ARCHIVE_FILE) -aoa $(DATA_DIR)
	@echo "Archivos descomprimidos en $(DATA_DIR)."

# Regla para seleccionar y ejecutar el .jar
choose_run:
	@echo "##################################################"
	@echo "#####                                        #####"
	@echo "#####        Recommender system              #####"
	@echo "#####                                        #####"
	@echo "##################################################"
	@echo "#####                                        #####"
	@echo "#####        Select the version to run       #####"
	@echo "#####        [1] Terminal version            #####"
	@echo "#####        [2] GUI version                 #####"
	@echo "#####                                        #####"
	@echo "##################################################"
	@read -p "Option: " option; \
	if [ $$option -eq 1 ]; then \
		$(MAKE) run_terminal; \
	elif [ $$option -eq 2 ]; then \
		$(MAKE) run_gui; \
	else \
		echo "Invalid option. Execution cancelled."; \
	fi

# Regla para ejecutar la versión de terminal del JAR
run_terminal:
	@echo "Ejecutando $(JAR_TERMINAL)..."
	@java -jar $(JAR_TERMINAL)

# Regla para ejecutar la versión GUI del JAR
run_gui:
	@echo "Ejecutando $(JAR_GUI)..."
	@java -jar $(JAR_GUI)

# Limpiar los archivos descomprimidos
clean:
	@echo "Eliminando carpeta $(DATA_DIR)..."
	@rm -rf $(DATA_DIR)
	@echo "Carpeta eliminada."


