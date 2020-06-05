package bdd;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.TreeSet;

/**
 * Classe qui contient des outils de sérialization
 *
 * @author Jason Mahdjoub
 * @version 1.0
 */
class SerializationTools {
	/**
	 * Serialise/binarise l'objet passé en paramètre pour retourner un tableau binaire
	 * @param o l'objet à serialiser
	 * @return the tableau binaire
	 * @throws IOException si un problème d'entrée/sortie se produit
	 */
	static byte[] serialize(Serializable o) throws IOException {
		//complete

		if(o == null) {
			throw new NullPointerException();
		}
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		objectOutputStream.writeObject(o);
		objectOutputStream.flush();

		return byteArrayOutputStream.toByteArray();
	}

	/**
	 * Désérialise le tableau binaire donné en paramètre pour retrouver l'objet initial avant sa sérialisation
	 * @param data le tableau binaire
	 * @return l'objet désérialisé
	 * @throws IOException si un problème d'entrée/sortie se produit
	 * @throws ClassNotFoundException si un problème lors de la déserialisation s'est produit
	 */
	static Serializable deserialize(byte[] data) throws IOException, ClassNotFoundException {
		//complete
		/*
		if(data == null) {
			throw new NullPointerException();
		}
		*/

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
		ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

		Serializable object = (Serializable) objectInputStream.readObject();
		byteArrayInputStream.close();
		objectInputStream.close();
		return object;
	}

	/**
	 * Serialise/binarise le tableau d'espaces libres passé en paramètre pour retourner un tableau binaire, mais selon le schéma suivant :
	 * Pour chaque interval ;
	 * <ul>
	 *     <li>écrire en binaire la position de l'interval</li>
	 *     <li>écrire en binaire la taille de l'interval</li>
	 * </ul>
	 * Utilisation pour cela la classe {@link DataOutputStream}
	 *
	 * @param freeSpaceIntervals le tableau d'espaces libres
	 * @return un tableau binaire
	 * @throws IOException si un problème d'entrée/sortie se produit
	 */
	static byte[] serializeFreeSpaceIntervals(TreeSet<BDD.FreeSpaceInterval> freeSpaceIntervals) throws IOException {
		//complete
		if(freeSpaceIntervals == null) {
			throw new NullPointerException();
		}

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

		for(BDD.FreeSpaceInterval interval : freeSpaceIntervals) {

			dataOutputStream.writeLong(interval.getStartPosition());
			dataOutputStream.writeLong(interval.getLength());
			dataOutputStream.flush();
		}

		return byteArrayOutputStream.toByteArray();
	}

	/**
	 * Effectue l'opération inverse de la fonction {@link #serializeFreeSpaceIntervals(TreeSet)}
	 * @param data le tableau binaire
	 * @return le tableau d'espaces libres
	 * @throws IOException si un problème d'entrée/sortie se produit
	 */
	static TreeSet<BDD.FreeSpaceInterval> deserializeFreeSpaceIntervals(byte[] data) throws IOException {
		if(data == null) {
			throw new NullPointerException();
		}

		TreeSet<BDD.FreeSpaceInterval> _freeSpaceInterval = new TreeSet<BDD.FreeSpaceInterval>();
		byte[] array1 = new byte[8];
		byte[] array2 = new byte[8];

		ByteArrayInputStream arrayInput = new ByteArrayInputStream(data);

		while (arrayInput.read(array1) != -1 && arrayInput.read(array2) != -1) {
			_freeSpaceInterval.add(new BDD.FreeSpaceInterval(
					ByteBuffer.wrap(array1).getLong(),
					ByteBuffer.wrap(array2).getLong()
			));
		}
		return _freeSpaceInterval;
	}
}
